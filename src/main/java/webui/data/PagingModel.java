package webui.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * com.vaadin.incubator.pagingcomponent.data.PagingModel, created on 25-Aug-2009
 * 10:33:06
 * <p>
 * 
 * @author charles
 */
public class PagingModel {

	private static final int DEFAULT_PAGES_TO_DISPLAY = 5;

	/**
	 * The total number of pages available
	 */
	private int totalNumberOfPages;
	/**
	 * The maximum number of page numbers to display
	 */
	private int numPageNumbersToDisplay;
	/**
	 * The number of the current page
	 */
	private int currentPage = 1;
	/**
	 * Used to manage listeners
	 */
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public PagingModel(int totalNumberOfPages) {
		this(totalNumberOfPages, DEFAULT_PAGES_TO_DISPLAY);
	}

	public PagingModel(int totalNumberOfPages, int numPageNumbersToDisplay) {
		this.totalNumberOfPages = totalNumberOfPages;
		this.numPageNumbersToDisplay = numPageNumbersToDisplay;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		int oldValue = this.currentPage;
		this.currentPage = currentPage;
		pcs.firePropertyChange("currentPage", oldValue, currentPage);
	}

	public int getTotalNumberOfPages() {
		return totalNumberOfPages;
	}

	public void setTotalNumberOfPages(int totalNumberOfPages) {
		int oldValue = this.totalNumberOfPages;
		this.totalNumberOfPages = totalNumberOfPages;
		pcs.firePropertyChange("totalNumberOfPages", oldValue, totalNumberOfPages);
	}

	public int getNumPageNumbersToDisplay() {
		return numPageNumbersToDisplay;
	}

	public void setNumPageNumbersToDisplay(int numPageNumbersToDisplay) {
		int oldValue = this.numPageNumbersToDisplay;
		this.numPageNumbersToDisplay = numPageNumbersToDisplay;
		pcs.firePropertyChange("numPageNumbersToDisplay", oldValue, numPageNumbersToDisplay);
	}

	public int getPreviousPage() {
		return currentPage - 1 < 1 ? 1 : currentPage - 1;
	}

	public int getNextPage() {
		return currentPage + 1 > totalNumberOfPages ? totalNumberOfPages : currentPage + 1;
	}

	public int getLastPageNumberToDisplay() {
		int rangeEnd = currentPage <= (numPageNumbersToDisplay / 2) ? numPageNumbersToDisplay : currentPage
				+ (this.numPageNumbersToDisplay / 2);

		return rangeEnd > totalNumberOfPages ? totalNumberOfPages : rangeEnd;
	}

	public int getFirstPageNumbertoDisplay() {
		int rangeStart = getLastPageNumberToDisplay() - numPageNumbersToDisplay + 1;
		return rangeStart < 1 ? 1 : rangeStart;
	}

	public boolean isFirstPage() {
		return currentPage == 1;
	}

	public boolean hasNextPage() {
		return currentPage < totalNumberOfPages;
	}

	public boolean hasPreviousPage() {
		return currentPage > 1;
	}

	public boolean isLastPage() {
		return currentPage == totalNumberOfPages;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

}