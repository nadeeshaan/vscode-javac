package org.javacs;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import javax.lang.model.element.Element;
import org.junit.Ignore;
import org.junit.Test;

public class SymbolUnderCursorTest {

    @Test
    public void classDeclaration() {
        assertEquals(
                "SymbolUnderCursor", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 3, 22));
    }

    @Test
    public void fieldDeclaration() {
        assertEquals("field", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 4, 22));
    }

    @Test
    public void methodDeclaration() {
        assertEquals("method", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 6, 22));
    }

    @Test
    public void methodParameterDeclaration() {
        assertEquals(
                "methodParameter", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 6, 36));
    }

    @Test
    public void localVariableDeclaration() {
        assertEquals(
                "localVariable", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 7, 22));
    }

    @Test
    public void constructorParameterDeclaration() {
        assertEquals(
                "constructorParameter",
                symbolAt("/org/javacs/example/SymbolUnderCursor.java", 17, 46));
    }

    @Test
    public void classIdentifier() {
        assertEquals(
                "SymbolUnderCursor",
                symbolAt("/org/javacs/example/SymbolUnderCursor.java", 12, 23));
    }

    @Test
    public void fieldIdentifier() {
        assertEquals("field", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 9, 27));
    }

    @Test
    public void methodIdentifier() {
        assertEquals("method", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 12, 12));
    }

    @Test
    public void methodSelect() {
        assertEquals("method", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 13, 17));
    }

    @Ignore // tree.sym is null
    @Test
    public void methodReference() {
        assertEquals("method", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 14, 46));
    }

    @Test
    public void methodParameterReference() {
        assertEquals(
                "methodParameter", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 10, 32));
    }

    @Test
    public void localVariableReference() {
        assertEquals(
                "localVariable", symbolAt("/org/javacs/example/SymbolUnderCursor.java", 10, 16));
    }

    // Re-using the language server makes these tests go a lot faster, but it will potentially produce surprising output if things go wrong
    private static final JavaLanguageServer server = LanguageServerFixture.getJavaLanguageServer();

    private String symbolAt(String file, int line, int character) {
        Optional<Element> symbol = server.findSymbol(FindResource.uri(file), line, character);

        return symbol.map(s -> s.getSimpleName().toString()).orElse(null);
    }
}
