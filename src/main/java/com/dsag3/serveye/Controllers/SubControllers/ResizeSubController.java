package com.dsag3.serveye.Controllers.SubControllers;

import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ResizeSubController {
    // APP Elements
    private Stage app;
    private Region topRegion;
    private Region topLeftRegion;
    private Region topRightRegion;
    private Region rightRegion;
    private Region bottomRegion;
    private Region bottomLeftRegion;
    private Region bottomRightRegion;
    private Region leftRegion;
    // Sub Controller Specific Fields
    private final Delta prevSize = new Delta();
    private final Delta prevPos = new Delta();
    private boolean snapped;
    private final String bottom = "bottom";

    public ResizeSubController(Stage app,
                               Region topRegion,
                               Region topLeftRegion,
                               Region topRightRegion,
                               Region rightRegion,
                               Region bottomRegion,
                               Region bottomLeftRegion,
                               Region bottomRightRegion,
                               Region leftRegion) {
        this.app = app;
        this.topRegion = topRegion;
        this.topLeftRegion = topLeftRegion;
        this.topRightRegion = topRightRegion;
        this.rightRegion = rightRegion;
        this.bottomRegion = bottomRegion;
        this.bottomLeftRegion = bottomLeftRegion;
        this.bottomRightRegion = bottomRightRegion;
        this.leftRegion = leftRegion;
    }
    public void init() {
        makeResizable(topRegion, "top");
        makeResizable(topLeftRegion, "top-left");
        makeResizable(topRightRegion, "top-right");
        makeResizable(rightRegion, "right");
        makeResizable(bottomRegion, bottom);
        makeResizable(bottomLeftRegion, bottom + "-left");
        makeResizable(bottomRightRegion, bottom + "-right");
        makeResizable(leftRegion, "left");
    }
    // Utility method
    public void makeResizable(Region resizeHandle, String direction) {
        resizeHandle.setOnDragDetected(event -> {
            prevSize.x = app.getWidth();
            prevSize.y = app.getHeight();
            prevPos.x = app.getX();
            prevPos.y = app.getY();
        });

        resizeHandle.setOnMouseDragged(m -> {
            if (m.isPrimaryButtonDown()) {
                double width = app.getWidth();
                double height = app.getHeight();

                // Horizontal resize.
                if (direction.endsWith("left") && !app.isMaximized()) {
                    double comingWidth = width - m.getScreenX() + app.getX();

                    //Check if it violates minimumWidth
                    if (comingWidth > 0 && comingWidth >= app.getMinWidth()) {
                        app.setWidth(app.getX() - m.getScreenX() + app.getWidth());
                        app.setX(m.getScreenX());
                    }

                } else if (direction.endsWith("right") && !app.isMaximized()) {
                    double comingWidth = width + m.getX();

                    //Check if it violates
                    if (comingWidth > 0 && comingWidth >= app.getMinWidth())
                        app.setWidth(m.getSceneX());
                }

                // Vertical resize.
                if (direction.startsWith("top") && !app.isMaximized()) {
                    if (snapped) {
                        app.setHeight(prevSize.y);
                        snapped = false;
                    } else if ((height > app.getMinHeight()) || (m.getY() < 0)) {
                        app.setHeight(app.getY() - m.getScreenY() + app.getHeight());
                        app.setY(m.getScreenY());
                    }
                } else if (direction.startsWith(bottom) && !app.isMaximized()) {
                    if (snapped) {
                        app.setY(prevPos.y);
                        snapped = false;
                    } else {
                        double comingHeight = height + m.getY();

                        //Check if it violates
                        if (comingHeight > 0 && comingHeight >= app.getMinHeight())
                            app.setHeight(m.getSceneY());
                    }

                }
            }
        });

        // Record application height and y position.
        resizeHandle.setOnMousePressed(m -> {
            if ((m.isPrimaryButtonDown()) && (!snapped)) {
                prevSize.y = app.getHeight();
                prevPos.y = app.getY();
            }

        });
    }
}

// utility class
class Delta {
    Double x;
    Double y;
}
