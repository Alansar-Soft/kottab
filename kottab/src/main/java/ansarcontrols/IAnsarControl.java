package ansarcontrols;

public interface IAnsarControl<T extends Object> extends IAnsarNode {
	public T fetchValue();

	public void insertValue(T val);
}
