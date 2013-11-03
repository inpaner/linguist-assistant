package rule.input;

import semantics.model.Constituent;

public abstract class Input {
    
    public abstract boolean evaluate(Constituent constituent);
}
