import org.lightdocs.model.*

def headerParam(name, closure) {
    def param = node.headerParameter(name)
    if (closure) {
        param.with closure
    }
    return param
}

def pathParam(name, closure) {
    def param = node.pathParameter(name)
    if (closure) {
        param.with closure
    }
    return param
}

def queryParam(name, closure) {
    def param = node.queryParameter(name)
    if (closure) {
        param.with closure
    }
    return param
}


/* ---------------------------------------------------
 * The following methods are part of the DSL implementation and clients should not rely on them.
 * They should be considered "private".
 * ---------------------------------------------------
 */
def methodMissing(String name, args) {
    def property = node.metaClass.getMetaProperty(name)
    if (property) {
        property.setProperty(node, args[0])
    }
}


node.with {
    ${docScript}  
}
