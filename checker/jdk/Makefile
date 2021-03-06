# To use, run this command:
#   make all
# which creates the jdk.jar file.

CTSYM ?= $(JAVA_HOME)/lib/ct.sym

CHECKER_DIRS = nullness lock

all: clean classes
# Extracts unannotated .class files from $(CTSYM)
	$(MAKE) unmodified
# Insert annotations in those files.
	$(MAKE) jaifs annotated
# Put those annotated .class files back in jdk.jar.
	$(MAKE) jdk.jar

## DO NOT INVOKE DIRECTLY.  USE "make all" INSTEAD.
# jdk.jar only contains those classes with annotations, not every class from
# the original JDK.
jdk.jar:
	cd annotated; jar cf ../jdk.jar .

# Helper

# All class files that contain an annotation, by directory
ANNOTATED_CLASSES_BY_DIR = $(shell find $(CHECKER_DIRS) -name '*.class' | sort)
# All class files that contain some annotation, possibly with duplicates.
# These, and only these, need to be extracted from $(CTSYM).
ANNOTATED_CLASSES = \
	$(patsubst nullness/build/%.class,annotated/%.class, \
      $(patsubst lock/build/%.class,annotated/%.class, \
        $(ANNOTATED_CLASSES_BY_DIR)))

# Extract from $(CTSYM) an unannotated version of every file that will
# eventually get any annotation.
unmodified: $(ANNOTATED_CLASSES)

# Delegates
clean: $(addsuffix .clean, $(CHECKER_DIRS))
	rm -rf jdk.jar annotated

classes: $(addsuffix .classes, $(CHECKER_DIRS))

jaifs: $(addsuffix .jaifs, $(CHECKER_DIRS))

annotated: $(addsuffix .annotated, $(CHECKER_DIRS))

annotated/%.class:
	mkdir -p annotated
# Extract exactly one file from $(CTSYM)
# Ignore errors for now; we get errors when building the JDK for Java 6 but looking for files that are in the annotated JDK because of Java 7.
	-cd annotated; unzip $(CTSYM) '8/$*.sig' > /dev/null
	-cp -R annotated/8/* annotated
	-mv annotated/$*.sig annotated/$*.class
	-rm -rf annotated/8


# Helper targets to invoke subdir make files
# "$*" expands to the stem that matched the "%".
%.clean:
	cd $*; $(MAKE) clean

%.classes:
	cd $*; JDKDIR=$* $(MAKE) classes

%.jaifs:
	cd $*; $(MAKE) jaifs

%.annotated:
	cd $*; $(MAKE) annotated

TAGS: tags
tags:
	etags `find . -name '*.java'`
