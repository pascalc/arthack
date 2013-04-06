arthack
=======

Ideas for Art Hack Day Stockholm 2013

	dialog.core=> (use 'clojure.pprint)
	(use 'clojure.pprint)
	nil
	dialog.core=> (pprint (take 10 (sentences 6)))
	("of the same time to time"
	 "of the same time of the"
	 "of the same as well as"
	 "of the same as soon as"
	 "in the same time to time"
	 "of the same as far as"
	 "of the same time to the"
	 "in the same time of the"
	 "of the same as it is"
	 "of the same time to be")
	nil

	dialog.core=>
	(pprint
	  (run 10 [q]
	    (fresh [w1 w2 w3 w4 w5]
	      (== w2 "soul")
	      (sentenceo 5 [w1 w2 w3 w4 w5])
	      (== q [w1 w2 w3 w4 w5]))))
	(["the" "soul" "of" "the" "world"]
	 ["the" "soul" "of" "his" "own"]
	 ["the" "soul" "of" "the" "same"]
	 ["the" "soul" "of" "the" "house"]
	 ["the" "soul" "of" "all" "the"]
	 ["the" "soul" "of" "the" "people"]
	 ["the" "soul" "of" "the" "day"]
	 ["the" "soul" "of" "the" "old"]
	 ["the" "soul" "of" "the" "great"]
	 ["the" "soul" "of" "the" "room"])
	nil
