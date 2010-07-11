package webui.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

import webui.data.PagingModel;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

/**
 * com.vaadin.incubator.pagingcomponent.ui.PagingComponent, created on
 * 26-Aug-2009 10:52:20
 * <p>
 * 
 * @author Charles
 */
public class PagingComponent extends CustomComponent implements Button.ClickListener, PropertyChangeListener {
	private Button bFirst = new Button("First", this);
	private Button bPrevious = new Button("Previous", this);
	private Button bNext = new Button("Next", this);
	private Button bLast = new Button("Last", this);
	private HorizontalLayout pageNumbers = new HorizontalLayout();

	private PagingModel model;

	public PagingComponent(PagingModel model) {
		this();
		setModel(model);
	}

	public PagingComponent() {
		setStyleName("paging");
		initButton(bFirst, "first");
		initButton(bPrevious, "previous");
		initButton(bNext, "next");
		initButton(bLast, "last");

		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.addComponent(bFirst);
		mainLayout.addComponent(bPrevious);
		mainLayout.addComponent(pageNumbers);
		mainLayout.addComponent(bNext);
		mainLayout.addComponent(bLast);

		setCompositionRoot(mainLayout);
		configureButtons();
	}

	public PagingModel getModel() {
		return model;
	}

	public void setModel(PagingModel model) {
		PagingModel oldValue = model;
		this.model = model;

		if (oldValue != null) {
			oldValue.removePropertyChangeListener(this);
		}

		if (model != null) {
			model.addPropertyChangeListener(this);

		}

		configureButtons();
	}

	private void configureButtons() {
		if (model == null) {
			bFirst.setEnabled(false);
			bPrevious.setEnabled(false);
			bNext.setEnabled(false);
			bLast.setEnabled(false);
		} else {
			/* Enable/Disable the navigation buttons */
			bFirst.setEnabled(!model.isFirstPage());
			bPrevious.setEnabled(model.hasPreviousPage());
			bNext.setEnabled(model.hasNextPage());
			bLast.setEnabled(!model.isLastPage());

			/* Refresh the page numbers displayed */
			pageNumbers.removeAllComponents();
			for (int pageNumber = model.getFirstPageNumbertoDisplay(); pageNumber <= model.getLastPageNumberToDisplay(); pageNumber++) {
				Button pageButton = new Button(Integer.toString(pageNumber), new PageClickListener(pageNumber));
				pageButton.setStyleName("link");
				if (pageNumber == model.getCurrentPage()) {
					pageButton.addStyleName("current-page");
				}
				pageNumbers.addComponent(pageButton);
			}
		}
	}

	private void initButton(Button button, String iconPath) {
		button.setStyleName("link");
		button.addStyleName("navigation-button");
		button.setIcon(new ThemeResource("img/go-" + iconPath + ".png"));
		button.setEnabled(false);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		configureButtons();
	}

	public void buttonClick(Button.ClickEvent event) {
		Button clicked = event.getButton();
		if (clicked == bFirst) {
			model.setCurrentPage(1);
		} else if (clicked == bPrevious) {
			model.setCurrentPage(model.getCurrentPage() - 1);
		} else if (clicked == bNext) {
			model.setCurrentPage(model.getCurrentPage() + 1);
		} else if (clicked == bLast) {
			model.setCurrentPage(model.getTotalNumberOfPages());
		}
	}

	private class PageClickListener implements Button.ClickListener, Serializable {
		private final int page;

		public PageClickListener(int page) {
			this.page = page;
		}

		public void buttonClick(Button.ClickEvent event) {
			model.setCurrentPage(page);
		}
	}
}