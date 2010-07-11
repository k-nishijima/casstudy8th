package kvs;

import java.util.List;

/**
 * KVSを操作するためのインターフェイス
 * 
 * @author west
 * 
 */
public interface KVSClient {

	/**
	 * 全てのカラムを明示的に指定してcount
	 * 
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 * @return
	 */
	public int count(String ksp, String cf, String key, String sp);

	/**
	 * 全てのカラムを明示的に指定してset
	 * 
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 * @param col
	 * @param value
	 */
	public void set(String ksp, String cf, String key, String sp, String col, String value);

	/**
	 * 全てのカラムを明示的に指定して複数のエンティティをset
	 * 
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 * @param cols
	 * @param values
	 */
	public void set(String ksp, String cf, String key, String sp, List<String> cols, List<String> values);

	/**
	 * 全てのカラムを明示的に指定してget_slice。ソート順はASC
	 * 
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 *            nullを指定するならgetKVSSuperEntityを使う
	 * @return KVSEntityの一覧
	 */
	public List<KVSEntity> getKVSEntities(String ksp, String cf, String key, String sp);

	/**
	 * keyまで指定してget_slice。ソート順はASC
	 * 
	 * @param ksp
	 * @param cf
	 * @param key
	 * @return KVSSuperEntityの一覧
	 */
	public List<KVSSuperEntity> getKVSSuperEntities(String ksp, String cf, String key);

	/**
	 * ソート順と全てのカラムを明示的に指定してget_slice
	 * 
	 * @param desc
	 *            true : DESC / false : ASC
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 *            nullを指定するならgetKVSSuperEntityを使う
	 * @return KVSEntityの一覧
	 */
	public List<KVSEntity> getKVSEntities(boolean desc, String ksp, String cf, String key, String sp);

	/**
	 * ソート順と全てのカラムを明示的に指定してget_slice
	 * 
	 * @param desc
	 *            true : DESC / false : ASC
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 * @return KVSSuperEntityの一覧
	 */
	public List<KVSSuperEntity> getKVSSuperEntities(boolean desc, String ksp, String cf, String key);

	/**
	 * 全てのカラムを明示的に指定してget
	 * 
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 * @param col
	 * @return 指定のキーが見つからなかった場合、nullを返す
	 */
	public KVSEntity getKVSEntity(String ksp, String cf, String key, String sp, String col);

	/**
	 * spを指定して削除
	 * 
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 */
	public void del(String ksp, String cf, String key, String sp);

	/**
	 * 全てのカラムを指定して削除
	 * 
	 * @param ksp
	 * @param cf
	 * @param key
	 * @param sp
	 * @param col
	 */
	public void del(String ksp, String cf, String key, String sp, String col);
}
