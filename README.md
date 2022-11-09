As I stated at the time of the test, my first idea was using a Dynamic Progamming approach that I had seen in a lesson from codility. Here's the source material for the theory:

https://codility.com/media/train/15-DynamicProgramming.pdf

The main idea behind the development is that there exists a cumulative relationship between the subsets that add to a target sum.
If there exists a subset _S_ from a set that adds to a number _a_, be it _n_ a number not in the subset _S_, there exists a subset _S2_ where _S2 = S + n_ and its sum is equals to _B = A + n_  

Be it n0 the first element of the number set, and f(i) the sum of all the members of the number set from n0 to ni (sorry I don't know how to do subscript). We know that:

(1) For if n0 and f(0) have the same value, there is a subset that adds to the target number f(0) (this is used for initial condition) 

(2) It applies to all the members, if ni = f(i), there is at least one subset (that single element) that adds to the target value.

(3) If I have a set which has a subset that adds to the target number, a bigger set will always have a subset that adds to the number.

Lets call Y the set. If, from the whole number set, I create a subset Y, and there is in Y a subset S that is equals to the target number, any Y2 that contains Y does the same.

What makes this work are the next recursive properties:

We know f(i) = sum(a0..ai)

Be it x an integer:

f(i) - x = sum(a0..ai) - x

now if x = ai

f(i) - ai = sum(a0..ai) - ai

we can say:

(4) f(i) - ai = sum(a0..a(i-1)) or f(i) - ai = f(i-1)

f(i) - ai = f(i-1) <- This is what I will use for the recursion

I will need all the possible f(i) - ai, and the lowest one that I can reach with my approach is the adittion of all the negative numbers.
(I've got this from trial and error and out of bound exceptions, at the moment I don't know the reason) 

So what I am going to do is to create a map where in a dimension I will have the number set, and the index i will mark a subset for the elements a0..ai.
And in the other all the possible target sums between the sum of all the negative numbers or zero and the target sum.
I will save here a boolean that indicates if there is a solution for f(i), being i the first dimension and f(i) the second one.

I will use (1) to initialize the first row, and (2), (3) and (4) to fill up the map. 
 
---

When I had the map I decided to trace paths of possible solutions.

When you take a number for a subset, you apply (4), you subtract it from the target sum, go to the new target sum, and since you removed it, if you are at that index you go to i-1 (a smaller subset).

You can either take it or, if there is a smaller subset than equals the target sum, ignore it by (3). That is, if the subset for i-1 has a solution (is true), you don't need to use i.

I start from the target sum and the biggest set possible, and I do all the possible paths. Instead of backtracing, I fork the two possibilities and add a tracker for it in a list. 

Any tracker which grows stale gets removed. If there is no numbers left to take (i0) and its too short or it doesn't add to the target it gets cut.

I will be looking for 4 nodes paths which add to the target.



I have been testing the code with the unit test. You can test it by running the QuadrupletsSumCheckerTests.testCheckers test.
The test case I used mainly for the development of the code was the test case 4. I found it was easier to work with it.

