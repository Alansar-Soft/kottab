package ansarcontrols;

public interface IAnsarControl<T extends Object> extends IAnsarNode {
	T fetchValue();

	void insertValue(T val);
}
