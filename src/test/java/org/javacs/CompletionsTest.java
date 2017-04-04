package org.javacs;

import com.google.common.collect.Lists;
import org.eclipse.lsp4j.CompletionItem;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CompletionsTest extends CompletionsBase {

    @Test
    public void staticMember() throws IOException {
        String file = "/org/javacs/example/AutocompleteStaticMember.java";

        // Static methods
        Set<String> suggestions = insertText(file, 5, 34);

        assertThat(suggestions, hasItems("fieldStatic", "methodStatic", "class"));
        assertThat(suggestions, not(hasItems("fields", "methods", "getClass")));
    }

    @Test
    public void staticReference() throws IOException {
        String file = "/org/javacs/example/AutocompleteStaticReference.java";

        // Static methods
        Set<String> suggestions = insertText(file, 7, 44);

        assertThat(suggestions, hasItems("methodStatic"));
        assertThat(suggestions, not(hasItems( "methods", "new")));
    }

    @Test
    public void member() throws IOException {
        String file = "/org/javacs/example/AutocompleteMember.java";

        // Static methods
        Set<String> suggestions = insertText(file, 5, 14);

        assertThat(suggestions, not(hasItems("fieldStatic", "methodStatic", "fieldStaticPrivate", "methodStaticPrivate", "class")));
        assertThat(suggestions, hasItems("fields", "methods", "fieldsPrivate", "methodsPrivate", "getClass"));
    }

    @Test
    public void throwsSignature() throws IOException {
        String file = "/org/javacs/example/AutocompleteMember.java";

        // Static methods
        Set<String> suggestions = items(file, 5, 14).stream().map(i -> i.getLabel()).collect(Collectors.toSet());

        assertThat(suggestions, hasItems("methods() throws Exception"));
    }

    @Test
    public void fieldFromInitBlock() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // f
        Set<String> suggestions = insertText(file, 8, 10);

        assertThat(suggestions, hasItems("fields", "fieldStatic", "methods", "methodStatic"));
    }

    @Test
    public void thisDotFieldFromInitBlock() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";
        
        // this.f
        Set<String> suggestions = insertText(file, 9, 15);

        assertThat(suggestions, hasItems("fields", "methods"));
        assertThat(suggestions, not(hasItems("fieldStatic", "methodStatic")));
    }

    @Test
    public void classDotFieldFromInitBlock() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";
        
        // AutocompleteMembers.f
        Set<String> suggestions = insertText(file, 10, 30);

        assertThat(suggestions, hasItems("fieldStatic", "methodStatic"));
        assertThat(suggestions, not(hasItems("fields", "methods")));

        // TODO
//        // this::m
//        suggestions = insertText(file, 10, 15);
//
//        assertThat(suggestions, hasItems("methods"));
//        assertThat(suggestions, not(hasItems("fields", "fieldStatic", "methodStatic")));
//
//        // AutocompleteMembers::m
//        suggestions = insertText(file, 11, 30);
//
//        assertThat(suggestions, hasItems("methodStatic"));
//        assertThat(suggestions, not(hasItems("fields", "fieldStatic", "methods")));
    }

    @Test
    public void fieldFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // f
        Set<String> suggestions = insertText(file, 22, 10);

        assertThat(suggestions, hasItems("fields", "fieldStatic", "methods", "methodStatic", "arguments"));
    }

    @Test
    public void thisDotFieldFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // this.f
        Set<String> suggestions = insertText(file, 23, 15);

        assertThat(suggestions, hasItems("fields", "methods"));
        assertThat(suggestions, not(hasItems("fieldStatic", "methodStatic", "arguments")));
    }

    @Test
    public void classDotFieldFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";
        
        // AutocompleteMembers.f
        Set<String> suggestions = insertText(file, 24, 30);

        assertThat(suggestions, hasItems("fieldStatic", "methodStatic"));
        assertThat(suggestions, not(hasItems("fields", "methods", "arguments")));
    }

    @Test
    public void thisRefMethodFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // this::m
        Set<String> suggestions = insertText(file, 25, 59);

        assertThat(suggestions, hasItems("methods"));
        assertThat(suggestions, not(hasItems("fields", "fieldStatic", "methodStatic")));
    }

    @Test
    public void classRefMethodFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // AutocompleteMembers::m
        Set<String> suggestions = insertText(file, 26, 74);

        assertThat(suggestions, hasItems("methodStatic"));
        assertThat(suggestions, not(hasItems("fields", "fieldStatic", "methods")));
    }

    @Test
    @Ignore // javac doesn't give us helpful info about the fact that static initializers are static
    public void fieldFromStaticInitBlock() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // f
        Set<String> suggestions = insertText(file, 16, 10);

        assertThat(suggestions, hasItems("fieldStatic", "methodStatic"));
        assertThat(suggestions, not(hasItems("fields", "methods")));
    }

    @Test
    public void classDotFieldFromStaticInitBlock() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // AutocompleteMembers.f
        Set<String> suggestions = insertText(file, 17, 30);

        assertThat(suggestions, hasItems("fieldStatic", "methodStatic"));
        assertThat(suggestions, not(hasItems("fields", "methods")));
    }

    @Test
    public void classRefFieldFromStaticInitBlock() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // AutocompleteMembers::m
        Set<String> suggestions = insertText(file, 17, 30);

        assertThat(suggestions, hasItems("methodStatic"));
        assertThat(suggestions, not(hasItems("fields", "fieldStatic", "methods")));
    }

    @Test
    public void fieldFromStaticMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // f
        Set<String> suggestions = insertText(file, 30, 10);

        assertThat(suggestions, hasItems("fieldStatic", "methodStatic", "arguments"));
        assertThat(suggestions, not(hasItems("fields", "methods")));
    }

    @Test
    public void classDotFieldFromStaticMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";
        
        // AutocompleteMembers.f
        Set<String> suggestions = insertText(file, 31, 30);

        assertThat(suggestions, hasItems("fieldStatic", "methodStatic"));
        assertThat(suggestions, not(hasItems("fields", "methods", "arguments")));
    }

    @Test
    public void classRefFieldFromStaticMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteMembers.java";

        // TODO
        // AutocompleteMembers::m
        Set<String> suggestions = insertText(file, 17, 30);

        assertThat(suggestions, hasItems("methodStatic"));
        assertThat(suggestions, not(hasItems("fields", "fieldStatic", "methods")));
    }

    private static String sortText(CompletionItem i) {
        if (i.getSortText() != null)
            return i.getSortText();
        else
            return i.getLabel();
    }

    @Test
    public void otherMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // new AutocompleteMember().
        Set<String> suggestions = insertText(file, 5, 34);

        assertThat(suggestions, not(hasItems("fieldStatic", "methodStatic", "class")));
        assertThat(suggestions, not(hasItems("fieldStaticPrivate", "methodStaticPrivate")));
        assertThat(suggestions, not(hasItems("fieldsPrivate", "methodsPrivate")));
        assertThat(suggestions, hasItems("fields", "methods", "getClass"));
    }

    @Test
    public void otherStatic() throws IOException {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // AutocompleteMember.
        Set<String> suggestions = insertText(file, 7, 28);

        assertThat(suggestions, hasItems("fieldStatic", "methodStatic", "class"));
        assertThat(suggestions, not(hasItems("fieldStaticPrivate", "methodStaticPrivate")));
        assertThat(suggestions, not(hasItems("fieldsPrivate", "methodsPrivate")));
        assertThat(suggestions, not(hasItems("fields", "methods", "getClass")));
    }

    @Test
    public void otherDotClassDot() throws IOException {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // AutocompleteMember.class.
        Set<String> suggestions = insertText(file, 8, 33);

        assertThat(suggestions, hasItems("getName", "getClass"));
        assertThat(suggestions, not(hasItems("fieldStatic", "methodStatic", "class")));
        assertThat(suggestions, not(hasItems("fieldStaticPrivate", "methodStaticPrivate")));
        assertThat(suggestions, not(hasItems("fieldsPrivate", "methodsPrivate")));
        assertThat(suggestions, not(hasItems("fields", "methods")));
    }

    @Test
    public void otherClass() throws IOException {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // Name of class
        Set<String> suggestions = insertText(file, 6, 10);

        // String is in root scope, List is in import java.util.*
        assertThat(suggestions, hasItems("AutocompleteOther", "AutocompleteMember", "ArrayList"));
    }

    @Test
    public void addImport() {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // Name of class
        List<? extends CompletionItem> items = items(file, 6, 10);

        for (CompletionItem item : items) {
            if ("ArrayList".equals(item.getLabel())) {
                assertThat(item.getAdditionalTextEdits(), not(nullValue()));
                assertThat(item.getAdditionalTextEdits(), not(empty()));

                return;
            }
        }

        fail("No ArrayList in " + items);
    }

    @Test
    public void dontImportSamePackage() {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // Name of class
        List<? extends CompletionItem> items = items(file, 6, 10);

        for (CompletionItem item : items) {
            if ("AutocompleteMember".equals(item.getLabel())) {
                assertThat(item.getAdditionalTextEdits(), either(empty()).or(nullValue()));

                return;
            }
        }

        fail("No AutocompleteMember in " + items);
    }

    @Test
    public void dontImportJavaLang() {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // Name of class
        List<? extends CompletionItem> items = items(file, 6, 10);

        for (CompletionItem item : items) {
            if ("ArrayIndexOutOfBoundsException".equals(item.getLabel())) {
                assertThat(item.getAdditionalTextEdits(), either(empty()).or(nullValue()));

                return;
            }
        }

        fail("No ArrayIndexOutOfBoundsException in " + items);
    }

    @Test
    public void dontImportSelf() {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // Name of class
        List<? extends CompletionItem> items = items(file, 6, 10);

        for (CompletionItem item : items) {
            if ("AutocompleteOther".equals(item.getLabel())) {
                assertThat(item.getAdditionalTextEdits(), either(empty()).or(nullValue()));

                return;
            }
        }

        fail("No AutocompleteOther in " + items);
    }

    @Test
    public void dontImportAlreadyImported() {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // Name of class
        List<? extends CompletionItem> items = items(file, 6, 10);

        for (CompletionItem item : items) {
            if ("Arrays".equals(item.getLabel())) {
                assertThat(item.getAdditionalTextEdits(), either(empty()).or(nullValue()));

                return;
            }
        }

        fail("No Arrays in " + items);
    }

    @Test
    public void dontImportAlreadyImportedStar() {
        String file = "/org/javacs/example/AutocompleteOther.java";

        // Name of class
        List<? extends CompletionItem> items = items(file, 6, 10);

        for (CompletionItem item : items) {
            if ("ArrayBlockingQueue".equals(item.getLabel())) {
                assertThat(item.getAdditionalTextEdits(), either(empty()).or(nullValue()));

                return;
            }
        }

        fail("No ArrayBlockingQueue in " + items);
    }

    @Test
    public void fromClasspath() throws IOException {
        String file = "/org/javacs/example/AutocompleteFromClasspath.java";

        // Static methods
        Set<String> suggestions = items(file, 8, 17).stream().map(i -> i.getLabel()).collect(Collectors.toSet());

        assertThat(suggestions, hasItems("add(E)", "add(int, E)"));
    }

    @Test
    public void betweenLines() throws IOException {
        String file = "/org/javacs/example/AutocompleteBetweenLines.java";

        // Static methods
        Set<String> suggestions = insertText(file, 9, 18);

        assertThat(suggestions, hasItems("add"));
    }

    @Test
    public void reference() throws IOException {
        String file = "/org/javacs/example/AutocompleteReference.java";

        // Static methods
        Set<String> suggestions = insertText(file, 7, 21);

        assertThat(suggestions, not(hasItems("methodStatic")));
        assertThat(suggestions, hasItems("methods", "getClass"));
    }

    @Test
    public void docstring() throws IOException {
        String file = "/org/javacs/example/AutocompleteDocstring.java";

        Set<String> docstrings = documentation(file, 8, 14);

        assertThat(docstrings, hasItems("A methods", "A fields"));

        docstrings = documentation(file, 12, 31);

        assertThat(docstrings, hasItems("A fieldStatic", "A methodStatic"));
    }

    @Test
    public void classes() throws IOException {
        String file = "/org/javacs/example/AutocompleteClasses.java";

        // Static methods
        Set<String> suggestions = insertText(file, 5, 10);

        assertThat(suggestions, hasItems("String", "SomeInnerClass"));
    }

    @Test
    public void editMethodName() throws IOException {
        String file = "/org/javacs/example/AutocompleteEditMethodName.java";

        // Static methods
        Set<String> suggestions = insertText(file, 5, 21);

        assertThat(suggestions, hasItems("getClass"));
    }

    @Test
    public void restParams() throws IOException {
        String file = "/org/javacs/example/AutocompleteRest.java";

        // Static methods
        Set<String> suggestions = items(file, 5, 18).stream().map(i -> i.getLabel()).collect(Collectors.toSet());

        assertThat(suggestions, hasItems("restMethod(String... params)"));
    }

    @Test
    public void constructor() throws IOException {
        String file = "/org/javacs/example/AutocompleteConstructor.java";

        // Static methods
        List<? extends CompletionItem> items = items(file, 5, 14);
        List<String> suggestions = Lists.transform(items, i -> i.getInsertText());

        assertThat(suggestions, hasItems("AutocompleteConstructor", "AutocompleteMember", "ArrayList"));

        for (CompletionItem each : items) {
            if (each.getInsertText().equals("ArrayList"))
                assertThat("new ? auto-imports", each.getAdditionalTextEdits(), both(not(empty())).and(not(nullValue())));
        }
    }

    @Test
    public void importFromSource() throws IOException {
        String file = "/org/javacs/example/AutocompletePackage.java";

        // Static methods
        Set<String> suggestions = insertText(file, 3, 12);

        assertThat("Does not have own package class", suggestions, hasItems("javacs"));
    }

    @Test
    public void importFromClasspath() throws IOException {
        String file = "/org/javacs/example/AutocompletePackage.java";

        // Static methods
        Set<String> suggestions = insertText(file, 5, 13);

        assertThat("Has class from classpath", suggestions, hasItems("util"));
    }

    @Test
    public void importFirstId() throws IOException {
        String file = "/org/javacs/example/AutocompletePackage.java";

        // Static methods
        Set<String> suggestions = insertText(file, 7, 9);

        assertThat("Has class from classpath", suggestions, hasItems("com", "org"));
    }

    @Test
    public void createEmptyLoader() throws ClassNotFoundException {
        URLClassLoader emptyClassLoader = ClassPathIndex.parentClassLoader();

        assertThat(emptyClassLoader.loadClass("java.util.ArrayList"), not(nullValue()));

        try {
            Class<?> found = emptyClassLoader.loadClass("com.google.common.collect.Lists");

            fail("Found " + found);
        } catch (ClassNotFoundException e) {
            // OK
        }
    }

    @Test
    public void emptyClasspath() throws IOException {
        String file = "/org/javacs/example/AutocompletePackage.java";

        // Static methods
        Set<String> suggestions = insertText(file, 6, 12);

        assertThat("Has deeply nested class", suggestions, not(hasItems("google.common.collect.Lists")));
    }

    @Test
    public void importClass() throws IOException {
        String file = "/org/javacs/example/AutocompletePackage.java";

        // Static methods
        List<? extends CompletionItem> items = items(file, 4, 25);
        List<String> suggestions = Lists.transform(items, i -> i.getLabel());

        assertThat(suggestions, hasItems("OtherPackagePublic"));
        assertThat(suggestions, not(hasItems("OtherPackagePrivate")));

        for (CompletionItem item : items) {
            if (item.getLabel().equals("OtherPackagePublic"))
                assertThat("Don't import when completing imports", item.getAdditionalTextEdits(), either(empty()).or(nullValue()));
        }
    }

    @Test
    public void fieldFromStaticInner() throws IOException {
        String file = "/org/javacs/example/AutocompleteOuter.java";

        // Initializer of static inner class
        Set<String> suggestions = insertText(file, 12, 14);

        assertThat(suggestions, hasItems("methodStatic", "fieldStatic"));
        assertThat(suggestions, not(hasItems("methods", "fields")));
    }

    @Test
    public void fieldFromInner() throws IOException {
        String file = "/org/javacs/example/AutocompleteOuter.java";

        // Initializer of inner class
        Set<String> suggestions = insertText(file, 18, 14);

        assertThat(suggestions, hasItems("methodStatic", "fieldStatic"));
        assertThat(suggestions, hasItems("methods", "fields"));
    }

    @Test
    public void classDotClassFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteInners.java";

        // AutocompleteInners.I
        Set<String> suggestions = insertText(file, 5, 29);

        assertThat("suggests qualified inner class declaration", suggestions, hasItem("InnerClass"));
        assertThat("suggests qualified inner enum declaration", suggestions, hasItem("InnerEnum"));
    }

    @Test
    public void innerClassFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteInners.java";

        // I
        Set<String> suggestions = insertText(file, 6, 10);

        assertThat("suggests unqualified inner class declaration", suggestions, hasItem("InnerClass"));
        assertThat("suggests unqualified inner enum declaration", suggestions, hasItem("InnerEnum"));
    }

    @Test
    public void newClassDotInnerClassFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteInners.java";

        // new AutocompleteInners.I
        Set<String> suggestions = insertText(file, 10, 33);

        assertThat("suggests qualified inner class declaration", suggestions, hasItem("InnerClass"));
        assertThat("suggests qualified inner enum declaration", suggestions, not(hasItem("InnerEnum")));
    }

    @Test
    public void newInnerClassFromMethod() throws IOException {
        String file = "/org/javacs/example/AutocompleteInners.java";

        // new I
        Set<String> suggestions = insertText(file, 11, 14);

        assertThat("suggests unqualified inner class declaration", suggestions, hasItem("InnerClass"));
        assertThat("suggests unqualified inner enum declaration", suggestions, not(hasItem("InnerEnum")));
    }

    @Test
    public void innerEnum() throws IOException {
        String file = "/org/javacs/example/AutocompleteInners.java";

        Set<String> suggestions = insertText(file, 15, 40);

        assertThat("suggests enum constants", suggestions, hasItems("Foo"));
    }

    @Test
    public void containsCharactersInOrder() {
        assertTrue(Completions.containsCharactersInOrder("FooBar", "FooBar"));
        assertTrue(Completions.containsCharactersInOrder("FooBar", "foobar"));
        assertTrue(Completions.containsCharactersInOrder("FooBar", "FB"));
        assertTrue(Completions.containsCharactersInOrder("FooBar", "fb"));
        assertFalse(Completions.containsCharactersInOrder("FooBar", "FooBar1"));
        assertFalse(Completions.containsCharactersInOrder("FooBar", "FB1"));
    }
}
