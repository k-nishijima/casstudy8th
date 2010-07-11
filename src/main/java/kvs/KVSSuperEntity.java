package kvs;

import java.util.List;

/**
 * KVS上の1SuperEntityを表す実装クラス
 * 
 * @author west
 * 
 */
public class KVSSuperEntity extends AbstractEntity {
	List<KVSEntity> children;
	String name;
	String value;

	public KVSSuperEntity(String name, String value, List<KVSEntity> children) {
		this.name = name;
		this.value = value;
		this.children = children;
	}

	@Override
	public List<KVSEntity> getChildren() {
		return children;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public boolean hasChild() {
		return true;
	}

}
