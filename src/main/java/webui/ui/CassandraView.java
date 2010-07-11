package webui.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import webui.AdminApplication;
import webui.data.CassandraPagingContainer;
import webui.data.PagingModel;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button.ClickEvent;

public class CassandraView extends Panel implements Button.ClickListener {
	private static final String[] cfs = new String[] { "Standard1", "Super1", "FooBarCF" };

	private TextField kspTextfiField = new TextField("keyspace:", "Keyspace1");
	private ComboBox cfCombo = new ComboBox("columnFamily:");
	private TextField keyTextfiField = new TextField("key:", "");
	private TextField spTextfiField = new TextField("superColumn:", "");
	private Button sliceButton = new Button("execute getSlice");

	private Panel dataPaging = null;
	private PagingModel model;
	private PagingComponent pagingComponent;
	private CassandraPagingContainer cassandraPagingContainer;

	public CassandraView(final AdminApplication app) {
		setCaption("Cassandra Data Viewer");
		setSizeFull();

		addComponent(kspTextfiField);
		kspTextfiField.setRequired(true);

		for (int i = 0; i < cfs.length; i++) {
			cfCombo.addItem(cfs[i]);
		}
		cfCombo.setFilteringMode(Filtering.FILTERINGMODE_OFF);
		cfCombo.setImmediate(true);
		cfCombo.setRequired(true);
		addComponent(cfCombo);
		addComponent(keyTextfiField);
		keyTextfiField.setRequired(true);
		addComponent(spTextfiField);

		sliceButton.addListener(this);
		addComponent(sliceButton);
	}

	private void showDataPaging(CmdGetSliceRequest request) {
		addComponent(getDataPaging(request));
	}

	private Component getDataPaging(CmdGetSliceRequest request) {
		if (dataPaging == null) {
			cassandraPagingContainer = new CassandraPagingContainer();
			cassandraPagingContainer.setCmdGetSliceRequest(request);
			cassandraPagingContainer.loadPage(1);
			model = new PagingModel(
					1 + (cassandraPagingContainer.getTotalRowCount() / CassandraPagingContainer.PAGE_SIZE));

			Table table = new Table();
			table.setImmediate(true);
			table.setWidth("100%");
			table.setContainerDataSource(cassandraPagingContainer);
			table.setSortDisabled(true);
			table.setColumnHeaders(CassandraPagingContainer.COL_HEADERS);

			pagingComponent = new PagingComponent(model);
			addComponent(pagingComponent);

			dataPaging = new Panel();
			dataPaging.addComponent(table);
			dataPaging.setSizeFull();

			model.addPropertyChangeListener("currentPage", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					cassandraPagingContainer.loadPage(model.getCurrentPage());
				}
			});
		} else {
			cassandraPagingContainer.setCmdGetSliceRequest(request);
			cassandraPagingContainer.loadPage(1);
		}
		return dataPaging;
	}

	public void buttonClick(ClickEvent event) {
		if (event.getButton().equals(sliceButton)) {
			String ksp = (String) kspTextfiField.getValue();
			// String cf = (String) cfTextfiField.getValue();
			String cf = (String) cfCombo.getValue();
			String key = (String) keyTextfiField.getValue();
			String sp = (String) spTextfiField.getValue();

			if (ksp == null || cf == null || key == null || sp == null) {
				getWindow().showNotification("key parameters Error");
			} else {
				if (sp.isEmpty()) {
					sp = null;
				}
				showDataPaging(new CmdGetSliceRequest(ksp, cf, key, sp));
			}
		}
	}

	/**
	 * getSliceリクエストクラス
	 * 
	 * @author west
	 * 
	 */
	public class CmdGetSliceRequest {
		public String ksp;
		public String cf;
		public String key;
		public String sp;

		public CmdGetSliceRequest(String ksp, String cf, String key, String sp) {
			this.ksp = ksp;
			this.cf = cf;
			this.key = key;
			this.sp = sp;
		}
	}
}
