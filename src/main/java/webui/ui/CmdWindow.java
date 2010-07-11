package webui.ui;

import webui.AdminApplication;

import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

/**
 * コマンド実行用Window
 * 
 * @author west
 * 
 */
public class CmdWindow extends Window implements Button.ClickListener {

	TextField foobarCli = new TextField("foobar:", "-wid ");
	Button checkSettingButton = new Button("実行");
	TextField resultArea;

	public CmdWindow(AdminApplication app) {
		setModal(true);
		setWidth("70%");
		center();
		setCaption("コマンド実行");

		addComponent(foobarCli);
		checkSettingButton.addListener((Button.ClickListener) this);
		addComponent(checkSettingButton);

		resultArea = new TextField("", "");
		resultArea.setRows(20);
		resultArea.setColumns(60);
		resultArea.setVisible(false);
		addComponent(resultArea);
	}

	public void buttonClick(ClickEvent event) {
		if (event.getButton().equals(checkSettingButton)) {
			// CheckSettingCommand cmd = new CheckSettingCommand();
			// CommandResult result = cmd.execute((String)
			// checkSettingCli.getValue());
			boolean result = false;
			if (result) {
				// resultArea.setValue(result.getMessage());
				// resultArea.setVisible(true);
			} else {
				getWindow().showNotification("usage:", "このコマンドの使い方です", Notification.TYPE_ERROR_MESSAGE);
			}
		}

	}

}