package ansarcontrols;

public class AnsarSearchBox extends AnsarHBox {
	private AnsarLabel title;
	private AnsarTextField idField;
	private AnsarTextField nameField;

	public AnsarSearchBox() {
		title = new AnsarLabel("title");
		idField = new AnsarTextField();
		nameField = new AnsarTextField();
	}
}
