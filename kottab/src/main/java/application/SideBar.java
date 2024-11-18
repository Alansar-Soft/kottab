package application;

import ansarcontrols.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utilities.*;

public class SideBar extends AnsarVBox
{
    private Stage stage;
    private Scene scene;
    private QuestionsDialog questionsDialog = new QuestionsDialog();

    public SideBar()
    {
        setPrefWidth(stage.getWidth() / 4);
        setPadding(new Insets(10));
        setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        setSpacing(20);
//        setStyle("-fx-border-style:solid; -fx-border-size: 2; -fx-border-color: black;  ");
        getStyleClass().add("nav-form");
        if (stage == null) stage = ResourceUtility.fetchStage();
        AnsarButton teacherBtn = new AnsarButton(Translator.translate("teachersScreen"));
        teacherBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.TeacherScreen), teacherBtn));
        AnsarButton studentBtn = new AnsarButton(Translator.translate("studentsScreen"));
        studentBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.StudentScreen), studentBtn));
        AnsarButton groupBtn = new AnsarButton(Translator.translate("groupsScreen"));
        groupBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.GroupScreen), groupBtn));
        AnsarButton memorizationNoteBookBtn = new AnsarButton(Translator.translate("memorizationNoteBook"));
        memorizationNoteBookBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.NoteBookScreen), memorizationNoteBookBtn));
        AnsarButton groupLevelBtn = new AnsarButton(Translator.translate("levelsScreen"));
        groupLevelBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.GroupLevelScreen), groupLevelBtn));
        AnsarButton userBtn = new AnsarButton(Translator.translate("usersScreen"));
        userBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.UserScreen), userBtn));
        AnsarButton absenceBtn = new AnsarButton(Translator.translate("absence"));
        absenceBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.AbsenceScreen), absenceBtn));
        AnsarButton paymentVoucherBtn = new AnsarButton(Translator.translate("paymentVoucher"));
        paymentVoucherBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.PaymentVoucher), paymentVoucherBtn));
        AnsarButton receiptVoucherBtn = new AnsarButton(Translator.translate("receiptVoucher"));
        receiptVoucherBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.ReceiptVoucher), receiptVoucherBtn));
        AnsarButton membershipBtn = new AnsarButton(Translator.translate("membership"));
        membershipBtn.setOnAction(e -> setOnAction(ResourceUtility.fetchCachedScreen(ScreensNames.Membership),
                membershipBtn));
        AnsarButton logoutBtn = new AnsarButton("logout");
        logoutBtn.setOnAction(e -> setOnAction(LoginScreen.fetchScreen(), logoutBtn));
        AnsarButton viewReport = new AnsarButton("viewReports");
        viewReport.setOnAction(e ->
        {
            questionsDialog.addQuestion("studentCode", ControlType.IntegerField);
            questionsDialog.addQuestion("fromDate", ControlType.DatePicker);
            questionsDialog.addQuestion("toDate", ControlType.DatePicker);
            questionsDialog.showDialog();
        });
        getChildren().addAll(teacherBtn, studentBtn, groupBtn, memorizationNoteBookBtn, groupLevelBtn, userBtn,
                absenceBtn, paymentVoucherBtn, receiptVoucherBtn, membershipBtn, viewReport, logoutBtn);
        getChildren().forEach(child ->
        {
            Button btn = (Button) child;
            btn.getStyleClass().add("nav-btn");
            btn.setPrefSize(130, 40);
        });
    }

    private void setOnAction(Scene scene, AnsarButton btn)
    {
        btn.requestFocus();
        this.scene = scene;
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }
}
