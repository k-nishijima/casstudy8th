package kvs;

import java.util.List;

/**
 * KVS上の1Entityを表す実装クラス
 * 
 * @author west
 * 
 */
public class KVSEntity extends AbstractEntity {
	String name;
	String value;

	public KVSEntity(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public List<KVSEntity> getChildren() {
		return null;
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
		return false;
	}

}
