BASENAME=expose
DISTNAME=expose_latex
DISTFOLDER?=$(shell pwd)

# OS detection
OS=$(shell uname)

# pdf viewer
ifeq ($(OS), Darwin)
	VIEWER=open
	VIEWER_OPTIONS=
else
	VIEWER=acroread
	VIEWER_OPTIONS=
endif


.PHONY: default
default: clean compile view

compile: 
	pdflatex $(BASENAME)
	bibtex $(BASENAME)
	pdflatex $(BASENAME)
	pdflatex $(BASENAME)

view:
	$(VIEWER) $(VIEWER_OPTIONS) $(BASENAME).pdf

zip: clean compile
	zip -9 -r --exclude=*.svn* $(BASENAME).zip imgs db.bib $(BASENAME).tex INSOexpose.cls Makefile README.txt $(BASENAME).pdf

.PHONY: clean
clean:
	rm -f *.aux *.bbl *.bla *.blg *.dvi *.loa *.lof *.log *.lot *.out *.pdf *.ps *.tdo *.toc *-blx.bib *run.xml comment.cut
	rm -f $(BASENAME).zip
