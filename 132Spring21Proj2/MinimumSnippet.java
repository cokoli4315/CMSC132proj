import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Document;

public class MinimumSnippet {
	// instance variables used to hold the document list and term list
	// last term, first term and distance betweenn terms
	public static List<String> termList = new ArrayList<String>();
	public static List<String> docList = new ArrayList<String>();
	public int end, start, length;
	public int tempStart, tempEnd, tempLength;
	public ArrayList<Integer> positionOfTerms;

	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		// new instances of the doclist temporary doclist temporary term list
		// and term list
		docList = new ArrayList<String>();
		termList = new ArrayList<String>();
		List<String> tempDocList = new ArrayList<String>();
		List<String> tempTermList = new ArrayList<String>();
		length = Integer.MAX_VALUE;
		// looping through document and placing each string inside an array list
		for (String docs : document) {
			docList.add(docs);
			tempDocList.add(docs);
		}
		// looping through term and placing each string inside an array list
		for (String term : terms) {
			termList.add(term);
			tempTermList.add(term);
		}
		// if there are no terms return illegal argument exception
		if (terms.size() == 0) {
			throw new IllegalArgumentException();
		} else {
			ArrayList<Integer> positions = new ArrayList<>();
			int counting = 0;
			while (tempDocList.containsAll(terms)) {
				positions = new ArrayList<Integer>();
				// loop through the doclist and the terms, for each occurance
				// place that position into an array list position
				for (String term : terms) {
					for (String term1 : tempDocList) {
						if (term.equals(term1)) {
							int position = tempDocList.indexOf(term);
							positions.add(position);
						}
					}
				}
				// sorts the position of each occurence of term in the document
				// in order to determine the end and start of the document
				Collections.sort(positions);
				tempEnd = positions.get(positions.size() - 1);
				tempStart = positions.get(0);
				// if the first term is at 0 add the end +1
				if (tempStart == 0) {
					tempLength = tempEnd + 1;
					// temp length is the end minus the starting term plus 1
				} else {
					tempLength = tempEnd - tempStart + 1;
				}
				//used to find the closest snippet, the distance between the snippet
				if (tempLength < length) {
					positionOfTerms = new ArrayList<Integer>();
					length = tempLength;
					start = tempStart + counting;
					end = tempEnd + counting;
					for (String term2 : terms) {
						positionOfTerms
								.add(tempDocList.indexOf(term2) + counting);
					}
				}
				//removes the snippet if the first occurance is at the first index of the document
				tempDocList.remove(tempStart);
				counting++;
			}
		}

	}

	public boolean foundAllTerms() {
		int count = termList.size();
		// if the termlist is empty return illegal argument exception
		if (count == 0) {
			throw new IllegalArgumentException();
		}
		// loops through the terms in the doclist and if the term is found the
		// termlist size decrements
		for (int index = 0; index < termList.size(); index++) {
			for (int j = 0; j < docList.size(); j++) {
				if (termList.get(index).equals(docList.get(j))) {
					count--;
					break;
				}
			}

		}
		// if the counter equals zero then all the terms were found
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

	// This returns the starting position of the first term
	public int getStartingPos() {
		return this.start;

	}

	// returns the ending position of a term in the document
	public int getEndingPos() {
		return this.end;

	}

	// Returns the length between end and start
	public int getLength() {
		return this.length;

	}

	// returns the position of the term in the document
	public int getPos(int index) {
		return positionOfTerms.get(index);

	}

}
