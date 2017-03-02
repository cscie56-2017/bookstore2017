package cscie56.demo


class SillyInterceptor {

    List<String> badJokes = ["What's brown and sticky? A stick.",
                     "Why do gorillas have big nostrils? Because gorillas have big fingers.",
                     "What sound does a nut make when it sneezes? Cashew!",
                     "Two windmills are on a date and one asks the other, \"So what kind of music do you like?\" The other replies, \"I'm a big metal fan!\"",
                     "What do you call someone else's cheese? Nacho Cheese!",
                      null,null,null,null,null]

    public SillyInterceptor() {
        match controller:'book'
    }

    boolean before() {
        flash.message = badJokes.get(new Random().nextInt(badJokes.size()))
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
