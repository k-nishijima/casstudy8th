package kvs;

import java.util.List;

/**
 * KVS上のEntityを表す抽象クラス
 * 
 * @author west
 * 
 */
public abstract class AbstractEntity {

	/**
	 * このエンティティの名前
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * このエンティティの値
	 * 
	 * @return
	 */
	public abstract String getValue();

	/**
	 * このエンティティの下にエンティティを持つか？
	 * 
	 * @return
	 */
	public abstract boolean hasChild();

	/**
	 * このエンティティの下のエンティティ一覧
	 * 
	 * @return
	 */
	public abstract List<KVSEntity> getChildren();

}