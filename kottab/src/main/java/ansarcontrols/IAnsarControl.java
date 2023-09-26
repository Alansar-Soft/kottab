package ansarcontrols;

public interface IAnsarControl<T extends Object> {
	public T fetchValue();

	public void insertValue(T val);

	public void reset();

}
