# Plan for the Gilded Rose Kata

## Step 0: Tech choice and setup
First choice we have to make is what programming language we are going to implement the Kata in.
As the job position i am applying for is mainly a Java role we will do the implementation in Java.

In the repo there is a reference to [texttets](texttests/README.md) This is great! this means we don't have to write
end-to-end tests ourselves. Followed the readme and checked if everything works accordingly. Reading into it the provided
"Golden Master" text with the current accepted output of the program.

## Step1: Requirements and exploring the code
Reading up on what has to happen, the following two things are expected of us.
First we need to improve the current design of the code.
and in a second step we need to add extra functionality for "conjured items"

Exploring the code we notice the following: There is one main "updateQuality" where all logic coincides. On top of
that the implementation of logic is quite messy. Second, no unit tests are provided. To have proper regression testing
we could use some unittest testing the logic of the different parts of the code on top of the end to end text test.

So the logical first step is to write unittests for the GildedRose class.


## Step2: Writing Unit Tests
Since no code can be tested in isolation but the driver code of GildedRose.UpdateQuality we will start there.
I will construct unit tests based on the requirements deconstructed in what i believe to be code that can be isolated
later on. We do best effort to cover edge cases.


## Step3: Refactor complex conditional statements
Before we actually start to refactor we can form an intuitive plan in what direction we want to refactor.
The requirements describe behaviour for 'types' of items. We probably want to end up isolating behaviour for each
item. Some resources about techniques to apply can be found here: 
[Refactoring Guru - Conditionals](https://refactoring.guru/refactoring/techniques/simplifying-conditional-expressions)

### Post Step 3 remarks:
I tried to make the commits small, so it is clear in what order i decide to refactor.
I also want to remark that [commit 34](https://github.com/RobbeHeirman/GildedRose-Conundra/commit/bdcb91ac0df73f3126cb9158a6a62a9dac919346)
is the simple solution. Quoting: [Simple is better then complex](https://peps.python.org/pep-0020/).
If the 'real world' use case was this small i think the refactors that will follow will just complicate things with marginal
generalization and adaptability benefits. But writing cool abstractions is fun and since this is a refactoring exercise we
will go a bit deeper into what further refactors we can appply.

### Step4: We are not enforcing the boundary values of our items?
The specs state items quality boundaries are 0 <= quality <= 50 this is enforced during the operations. This
is actually an invariant property on Item. We will not implement using DbC as this is an out-of-scope topic, but we
should probably do some precondition check before running our items 
(as we cannot check this on item construction because of the overpowered Goblin)

## Step5: What do we recognize in our current code setup?
Our code looks clean now, but potential future problems may arise with the current code setup. If more types
of items define we will need to write more methods to handle the behaviour in the GildedRoseApp. Resulting in one big 
class where all our methods will live in. One specific pattern comes to mind that solves [this](https://refactoring.guru/design-patterns/strategy)
The idea is that the GildedRose App only wants to update an item, but doesn't really need to hold the details of the
implementation.

## Step6: Now that we encapsulated the concrete logic 
The logic is split off, looks great! Still one issue tho... What if we want to open a seconded Gilded Rose with other
items and requirements? Gilded Rose depends on the StrategySelector... So let's change that!
Adding an interface for a strategyselector so we can easily switch out selectors. Added an extra constructor so that
the strategySelector can be injected.

## Step7: Now we can do something cool with runtime behaviour right
In this step i will showcase an example of what we can do now we decoupled the update logic from our driver class.
We created a builder as example to showcase we can now pass in anything adhering to our interface. Making it possible
to switch out the update logic of the items.

## Step8: Covering some lose ends
the following things still bother me
- We select our strategies in ever iteration. While those can actually be selected once. DONE
- Our logic has been split out but all our tests still test the Gilded Rose. They should be moved to there own units. DONE
- The pre-conditions of the item is tightly coupled with the GildedRose and should be moved. DONE
- Don't forget to implement the new request :P Done
- Document public functions Done

## Step9: We still have one issue.
Currently, we select a strategy based on the name. We assumed the name of the item can never change.
But with the current implementation of Item the name property is public. I think the name of an item should be constant
during the lifetime of the object (this makes the most sense to me) and is something mr goblin did not account for.
For now we will keep track of name changes and throw an error if this occurs. Other options could be to change
the strategy with the item or keep the current implementation.

