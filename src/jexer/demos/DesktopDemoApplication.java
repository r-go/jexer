/*
 * Jexer - Java Text User Interface
 *
 * The MIT License (MIT)
 *
 * Copyright (C) 2017 Kevin Lamonte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * @author Kevin Lamonte [kevin.lamonte@gmail.com]
 * @version 1
 */
package jexer.demos;

import java.io.*;
import java.util.*;

import jexer.*;
import jexer.event.*;
import jexer.menu.*;

/**
 * The demo application itself.
 */
public class DesktopDemoApplication extends TApplication {

    /**
     * Add all the widgets of the demo.
     */
    private void addAllWidgets() {

        // Add the menus
        addFileMenu();
        addEditMenu();
        addWindowMenu();
        addHelpMenu();

        final DesktopDemo desktop = new DesktopDemo(this);
        setDesktop(desktop);

        desktop.addButton("&Remove HATCH", 2, 5,
            new TAction() {
                public void DO() {
                    desktop.drawHatch = false;
                }
            }
        );
        desktop.addButton("&Show HATCH", 2, 8,
            new TAction() {
                public void DO() {
                    desktop.drawHatch = true;
                }
            }
        );

        final TWindow windowA = addWindow("Window A", 25, 14);
        final TWindow windowB = addWindow("Window B", 25, 14);
        windowA.addButton("&Show Window B", 2, 2,
            new TAction() {
                public void DO() {
                    windowB.show();
                }
            }
        );
        windowA.addButton("H&ide Window B", 2, 4,
            new TAction() {
                public void DO() {
                    windowB.hide();
                }
            }
        );
        windowA.addButton("&Maximize Window B", 2, 6,
            new TAction() {
                public void DO() {
                    windowB.maximize();
                }
            }
        );
        windowA.addButton("&Restore Window B", 2, 8,
            new TAction() {
                public void DO() {
                    windowB.restore();
                }
            }
        );
        windowB.addButton("&Show Window A", 2, 2,
            new TAction() {
                public void DO() {
                    windowA.show();
                }
            }
        );
        windowB.addButton("H&ide Window A", 2, 4,
            new TAction() {
                public void DO() {
                    windowA.hide();
                }
            }
        );
        windowB.addButton("&Maximize Window A", 2, 6,
            new TAction() {
                public void DO() {
                    windowA.maximize();
                }
            }
        );
        windowB.addButton("&Restore Window A", 2, 8,
            new TAction() {
                public void DO() {
                    windowA.restore();
                }
            }
        );

        desktop.addButton("S&how Window B", 25, 2,
            new TAction() {
                public void DO() {
                    windowB.show();
                }
            }
        );
        desktop.addButton("H&ide Window B", 25, 5,
            new TAction() {
                public void DO() {
                    windowB.hide();
                }
            }
        );
        desktop.addButton("Sh&ow Window A", 25, 8,
            new TAction() {
                public void DO() {
                    windowA.show();
                }
            }
        );
        desktop.addButton("Hid&e Window A", 25, 11,
            new TAction() {
                public void DO() {
                    windowA.hide();
                }
            }
        );
        desktop.addButton("&Create Window C", 25, 15,
            new TAction() {
                public void DO() {
                    final TWindow windowC = desktop.getApplication().addWindow(
                        "Window C", 30, 20, TWindow.NOCLOSEBOX);
                    windowC.addButton("&Close Me", 5, 5,
                        new TAction() {
                            public void DO() {
                                windowC.close();
                            }
                        }
                    );
                }
            }
        );

        desktop.addButton("Enable focusFollowsMouse", 25, 18,
            new TAction() {
                public void DO() {
                    DesktopDemoApplication.this.setFocusFollowsMouse(true);
                }
            }
        );
        desktop.addButton("Disable focusFollowsMouse", 25, 21,
            new TAction() {
                public void DO() {
                    DesktopDemoApplication.this.setFocusFollowsMouse(false);
                }
            }
        );

    }

    /**
     * Handle menu events.
     *
     * @param menu menu event
     * @return if true, the event was processed and should not be passed onto
     * a window
     */
    @Override
    public boolean onMenu(final TMenuEvent menu) {

        if (menu.getId() == TMenu.MID_OPEN_FILE) {
            try {
                String filename = fileOpenBox(".");
                 if (filename != null) {
                     try {
                         File file = new File(filename);
                         StringBuilder fileContents = new StringBuilder();
                         Scanner scanner = new Scanner(file);
                         String EOL = System.getProperty("line.separator");

                         try {
                             while (scanner.hasNextLine()) {
                                 fileContents.append(scanner.nextLine() + EOL);
                             }
                             new DemoTextWindow(this, filename,
                                 fileContents.toString());
                         } finally {
                             scanner.close();
                         }
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onMenu(menu);
    }

    /**
     * Public constructor.
     *
     * @param backendType one of the TApplication.BackendType values
     * @throws Exception if TApplication can't instantiate the Backend.
     */
    public DesktopDemoApplication(final BackendType backendType) throws Exception {
        super(backendType);
        addAllWidgets();
        getBackend().setTitle("Jexer Demo Application");
    }
}
