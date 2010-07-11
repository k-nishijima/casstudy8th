package webui.data;

import java.util.ArrayList;
import java.util.List;

import kvs.KVSEntity;
import kvs.KVSSuperEntity;
import webui.ui.CassandraView.CmdGetSliceRequest;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;

/**
 * Cassandra データ表示用コンテナ via
 * com.vaadin.incubator.pagingcomponent.demo.DemoPagingRowContainer
 * 
 * @author west
 * 
 */
public class CassandraPagingContainer extends AbstractRowContainer {
	private static final long serialVersionUID = 1L;
	public static final int PAGE_SIZE = 15;

	public static final String[] COL_HEADERS = new String[] { "keySpace", "columnFamily", "key", "superColumn",
			"column", "value" };

	private List<BeanItem<ColumnBean>> currentPage = new ArrayList<BeanItem<ColumnBean>>();
	private int totalRowCount = 0;

	private CmdGetSliceRequest cmdGetSliceRequest;

	public CassandraPagingContainer() {
		addContainerProperty("ksp", String.class, "");
		addContainerProperty("cf", String.class, "");
		addContainerProperty("key", String.class, "");
		addContainerProperty("sp", String.class, "");
		addContainerProperty("name", String.class, "");
		addContainerProperty("value", String.class, "");

		setNumberOfRows(0);
	}

	/**
	 * 指定ページをロード
	 * 
	 * @param pageNumber
	 */
	public void loadPage(int pageNumber) {
		currentPage.clear();
		int start = (pageNumber - 1) * PAGE_SIZE;
		int end = pageNumber * PAGE_SIZE;

		List<BeanItem<ColumnBean>> items = new ArrayList<BeanItem<ColumnBean>>(PAGE_SIZE);

		this.currentPage.clear();

		totalRowCount = 0;
		String key = cmdGetSliceRequest.key;
		if (cmdGetSliceRequest.sp == null || cmdGetSliceRequest.sp.isEmpty()) {
			List<KVSSuperEntity> superEntities = client.getKVSSuperEntities(true, cmdGetSliceRequest.ksp,
					cmdGetSliceRequest.cf, key);
			for (KVSSuperEntity superEntity : superEntities) {
				for (KVSEntity entity : superEntity.getChildren()) {
					if (start <= totalRowCount && totalRowCount < end) {
						items.add(new BeanItem<ColumnBean>(new ColumnBean(cmdGetSliceRequest.ksp,
								cmdGetSliceRequest.cf, cmdGetSliceRequest.key, superEntity.getName(), entity.getName(),
								entity.getValue())));
					}
					totalRowCount++;
				}
			}
		} else {
			List<KVSEntity> entities = client.getKVSEntities(true, cmdGetSliceRequest.ksp, cmdGetSliceRequest.cf, key,
					cmdGetSliceRequest.sp);
			for (KVSEntity entity : entities) {
				if (start <= totalRowCount && totalRowCount < end) {
					items.add(new BeanItem<ColumnBean>(new ColumnBean(cmdGetSliceRequest.ksp, cmdGetSliceRequest.cf,
							cmdGetSliceRequest.key, cmdGetSliceRequest.sp, entity.getName(), entity.getValue())));
				}
				totalRowCount++;
			}
		}

		currentPage.addAll(items);
		setNumberOfRows(items.size());
	}

	public class ColumnBean {
		private String ksp;
		private String cf;
		private String key;
		private String sp;
		private String name;
		private String value;

		public ColumnBean(String ksp, String cf, String key, String sp, String name, String value) {
			this.ksp = ksp;
			this.cf = cf;
			this.key = key;
			this.sp = sp;
			this.name = name;
			this.value = value;
		}

		public String getKsp() {
			return ksp;
		}

		public String getCf() {
			return cf;
		}

		public String getKey() {
			return key;
		}

		public String getSp() {
			return sp;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}

	public void setCmdGetSliceRequest(CmdGetSliceRequest cmdGetSliceRequest) {
		this.cmdGetSliceRequest = cmdGetSliceRequest;
		loadPage(1);
	}

	/**
	 * @see com.vaadin.data.Container#getItem(java.lang.Object)
	 */
	public Item getItem(Object itemId) {
		return currentPage.get((Integer) itemId);
	}

	/**
	 * 全体の行数を返す
	 * 
	 * @return
	 */
	public int getTotalRowCount() {
		return totalRowCount;
	}
}