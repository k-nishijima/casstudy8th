package webui;

import webui.ui.CassandraView;
import webui.ui.CmdWindow;
import webui.ui.NavigationTree;

import com.vaadin.Application;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class AdminApplication extends Application implements ItemClickListener, Button.ClickListener {
	private NavigationTree tree = new NavigationTree(this);

	private Button cmdButton = new Button("コマンド実行");
	private Button helpButton = new Button("ヘルプ");
	private SplitPanel horizontalSplit = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);

	private CassandraView cassandraView;
	private CmdWindow cmdWindow;

	@Override
	public void init() {
		buildMainLayout();
		setTheme("runo");
	}

	private void buildMainLayout() {
		setMainWindow(new Window("sample WebUI"));
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();

		layout.addComponent(createToolbar());
		layout.addComponent(horizontalSplit);
		layout.setExpandRatio(horizontalSplit, 1);

		horizontalSplit.setSplitPosition(150, SplitPanel.UNITS_PIXELS);
		horizontalSplit.setFirstComponent(tree);
		getMainWindow().setContent(layout);
	}

	public HorizontalLayout createToolbar() {
		HorizontalLayout lo = new HorizontalLayout();
		lo.addComponent(cmdButton);
		cmdButton.addListener((Button.ClickListener) this);
		lo.addComponent(helpButton);
		return lo;
	}

	private void setMainComponent(Component c) {
		horizontalSplit.setSecondComponent(c);
	}

	/**
	 * @see com.vaadin.event.ItemClickEvent.ItemClickListener#itemClick(com.vaadin.event.ItemClickEvent)
	 */
	public void itemClick(ItemClickEvent event) {
		if (event.getSource() == tree) {
			Object itemId = event.getItemId();
			if (itemId != null) {
				if (NavigationTree.CA.equals(itemId)) {
					showCassandraView();
				}
			}
		}

	}

	private void showCassandraView() {
		setMainComponent(getCassandraView());
	}

	private Component getCassandraView() {
		if (cassandraView == null) {
			cassandraView = new CassandraView(this);
		}
		return cassandraView;
	}

	private Window getCmdWindow() {
		if (cmdWindow == null) {
			cmdWindow = new CmdWindow(this);
		}
		return cmdWindow;
	}

	public void buttonClick(ClickEvent event) {
		getMainWindow().addWindow(getCmdWindow());
	}

}
