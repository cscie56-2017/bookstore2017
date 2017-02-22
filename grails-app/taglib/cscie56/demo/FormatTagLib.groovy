package cscie56.demo

class FormatTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    static namespace = "bookstore"
    /**
     * @attr from
     * @attr to REQUIRED
     * @attr by
     */
    def copyright = { attrs ->
        def by = attrs.by?:'President and Fellows of Harvard University'
        if (attrs.from){
            out << "<p>Copyright &copy; ${attrs.from}-${attrs.to} ${by}</p>".encodeAsRaw()
        } else {
            out << "<p>Copyright &copy; ${attrs.to} ${by}</p>".encodeAsRaw()
        }

    }
}
