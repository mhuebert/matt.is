# Rewrite a Rant

_Linus Torvalds, principal force behind the Linux kernel:_

~~Where do I start a petition to raise the IQ and kernel knowledge of people?~~ Guys, go read drivers/char/random.c. ~~Then, learn about cryptography. Finally, come back here and admit to the world that you were wrong. Short answer: we actually know what we are doing. You don't. Long answer:~~ we use rdrand as _one_ of many inputs into the random pool, and we use it as a way to \_improve_ that random pool. So even if rdrand were to be back-doored by the NSA, our use of rdrand actually improves the quality of the random numbers you get from /dev/random. ~~Really short answer: you're ignorant.~~ ([link](http://www.theregister.co.uk/2013/09/10/torvalds_on_rrrand_nsa_gchq))

_and_

Ok. I still really despise ~~the absolute incredible sh*t that is~~
non-discoverable buses, ~~and I hope that ARM SoC hardware designers all
die in some incredibly painful accident.~~ DT only does so much.

~~So if you see any, send them my love, and possibly puncture the
brake-lines on their car and put a little surprise in their coffee,
ok?~~ ([link](http://www.theregister.co.uk/2013/09/11/torvalds_suggests_poison_and_sabotage_for_arm_soc_designers/))