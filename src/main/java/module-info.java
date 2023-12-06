module com.dsag3.serveye {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.dsag3.serveye to javafx.fxml;
    opens com.dsag3.serveye.Controllers to javafx.fxml;
    exports com.dsag3.serveye;
    exports com.dsag3.serveye.Controllers;
    exports com.dsag3.serveye.Controllers.SubControllers;
    opens com.dsag3.serveye.Controllers.SubControllers to javafx.fxml;
    opens com.dsag3.serveye.Models to java.sql;
}