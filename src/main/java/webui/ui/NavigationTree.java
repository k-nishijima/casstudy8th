package webui.ui;

import webui.AdminApplication;

import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;

public class NavigationTree extends Tree {
	public static final Object CLUSTER = "Cluster";
	public static final Object MR_JOB = "MapReduce Job";
	public static final Object JOB_HISTORY = "JobHistory";
	public static final Object CA = "Cassandra";

	public NavigationTree(AdminApplication app) {
		addItem(CLUSTER);
		addItem(MR_JOB);
		addItem(JOB_HISTORY);
		addItem(CA);

		setSelectable(true);
		setNullSelectionAllowed(false);
		addListener((ItemClickListener) app);
	}

}
