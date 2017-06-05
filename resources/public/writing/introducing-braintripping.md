# Introducing BrainTripping

[BrainTripping](http://www.braintripping.com) is my latest labour of love. It's a whimsical experiment which turns the world's textual culture into an open-ended game of storytelling, parody, personality and poetry. After months of hilarity among friends, it's now ready for you to try.

So what is it?

![](/images/posts/last-supper.png)

BrainTripping lets you make up sentences and conversations using the words of Jesus, Shakespeare, Tupac, Kim Jong Il, Steve Jobs, and other cultural icons. 

![](/images/posts/brain-brains.png)


"Tripping" on a brain is an unpredictable experience that's kind of like improv. As you type, word suggestions from the brain appear. The words are partly random, and partly based on the brain's typical language patterns; hitting "tab" displays more random suggestions. You can only use words in the brain's vocabulary.

![](/images/posts/brain-writersblock.png)


Instead of trying to generate entire sentences, the interface is designed to help _you_ be creative. There's a genuine interaction between you and the brain that I personally enjoy a lot. It's impossible to get writer's block when you have these fascinating people feeding you new ideas with each keystroke. 

Completed trips are unmistakeably influenced by the brain, but it's obvious that neither the user nor the brain would have ever come up with the sentence on their own.

![](/images/posts/brain-example.png)


After you create a trip, other users can reply using any brain on the site.

![](/images/posts/brain-reply.png)


To build the language models, I've scoured publicly available material written, spoken, or sung by each brain. In some cases, the model includes material that's never been transcribed before (eg/ Kurt Cobain's journals). For Jesus, I used a copy of the New Testament which marked Jesus's words in red, and used OCR software which retained color information so that I could strip away everything else.


The interface lets you search each brain's vocabulary using wildcards (==*==), so you can do interesting searches like words that begin in "a" and end with "n" (a*n), or words that end in "eat" (*eat). It makes rhyming easy.


![](/images/posts/brain-rhyme.png)


While building this, I've been amazed by how much meaning is locked up in these old texts. At first, it seemed mostly like a gimmick that might be interesting for a few minutes at most, but I've been playing with this thing for months, and it just doesn't get old. It's as extensible as language itself; when you've got 30k+ words of Shakespeare at your disposal, the combinations are endless.


![](/images/posts/brain-shakes.png)

One of my motivations was to see if I could come up with a novel way to interact with authors you have an emotional connection to, but aren't alive anymore. The probabilistic model I use isn't anything close to artificial intelligence, but it offers a connection to unexpected sentiments and ideas which are distinctly influenced by the personality of the character, and the interaction has the open-ended flavour of conversation. 


My friends and I have been having a ridiculous amount of [fun](http://www.braintripping.com/trips/4fb2da4dff030a030000027b) with this over the past few weeks. I'd love for you to [give it a whirl](http://www.braintripping.com) and tell me what you think.

Matt

----

I'd like to give a huge shout out to the awesomely friendly Montréal tech community. In particular, BrainTripping took a big leap forward at last December's [node.js hackathon,](http://montrealtechwatch.com/2011/12/18/hackmtl-gathers-72-developers-hacking-on-nodejs-redis-and-mongodb/) where I was stunned by the presence of a veritable squadron of linguists. 

I owe a debt of gratitude to 
[Gina Cook](https://github.com/cesine), [Emmy Cathcart](https://github.com/mecathcart) and [Hisako Noguchi](http://linguistics.concordia.ca/gazette.html) from  [iLanguage Labs](http://ilanguage.ca/), as well as [Brian Doherty](https://github.com/bfdoherty-2008) and [Pablo Duboue](http://duboue.net), for helping me improve my approach to language modeling. Further, the team was rounded out by the talented [Jon Volkmar](https://github.com/j_v), [Jeff Marshall](https://github.com/jeffmarshall), and [Martin Provencher](https://github.com/vivrass), who helped us crank out a working multi-brain demo by the day's end.