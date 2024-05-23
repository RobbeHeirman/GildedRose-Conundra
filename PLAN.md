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