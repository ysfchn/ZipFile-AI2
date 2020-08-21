package com.yusufcihan.Zip;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;

import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.YailList;

import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.Collections;
import java.io.File;
import java.io.IOException;

import android.app.Activity;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;

@DesignerComponent(version = 1,
        description = "Java ZipFile implementation for App Inventor 2.<br>"
                    + "- Yusuf Cihan",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "https://yusufcihan.com/img/zip.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.READ_EXTERNAL_STORAGE")
public class Zip extends AndroidNonvisibleComponent implements Component {

    // ------------------------
    //       VARIABLES
    // ------------------------

    /* 
        -----------------------
        String ZIPFILE

        Specifies the file path of the zip file.

        -----------------------
    */
    private ZipFile ZIPFILE = null;
    private final Activity activity;

    public Zip(ComponentContainer container) {
        super(container.$form());
        activity = container.$context();
    }

    @SimpleFunction(description =
            "Opens a new zip archive.")
    public void File(final String path) {
        try {
            AsynchUtil.runAsynchronously(new Runnable() {
                @Override
                public void run() {
                    try {
                        ZIPFILE = new ZipFile(new File(path), ZipFile.OPEN_READ);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ZipFile(
                                    ZIPFILE.getName(),
                                    YailList.makeList(Collections.list(ZIPFILE.entries())), 
                                    ZIPFILE.getComment() == null ? "" : ZIPFILE.getComment(),
                                    ZIPFILE.size()
                                );
                            }
                        });
                    } catch (final Exception e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Error("ZipFile", e.getMessage());
                            }
                        });
                    }
                }
            });
        } catch (final Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Error("ZipFile", e.getMessage());
                }
            });
        }
        
    }

    @SimpleFunction(description =
            "Reads the content of one of file in the lastly opened archive.\n"
            + "Note that archive must be opened with ZipFile block first.")
    public void Entry(final String name) {
        try {
            if (ZIPFILE == null)
                throw new YailRuntimeError("Open a ZipFile first.", "Error");

            AsynchUtil.runAsynchronously(new Runnable() {
                @Override
                public void run() {
                    try {
                        final ZipEntry ZIPENTRY = ZIPFILE.getEntry(name);
                        if (ZIPENTRY == null)
                        {
                            throw new YailRuntimeError("Entry couldn't found in the archive.", "Error");
                        }
                        final String content = Read(ZIPFILE.getInputStream(ZIPENTRY));
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ZipEntry(ZIPENTRY.getName(), ZIPENTRY.isDirectory(), ZIPENTRY.getSize(), ZIPENTRY.getCrc(), content);
                            }
                        });
                    } catch (final Exception e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Error("ZipEntry", e.getMessage());
                            }
                        });
                    }
                }
            });
        } catch (final Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Error("ZipEntry", e.getMessage());
                }
            });
        }
        
    }

    @SimpleEvent(description = "Raises when one of methods resulted with error.")
	public void Error(String method, String message) {
		EventDispatcher.dispatchEvent(this, "Error", method, message);
    }

    @SimpleEvent(description = "Raises after ZipFile has opened successfully.")
	public void ZipFile(String name, YailList entries, String comment, int entryCount) {
		EventDispatcher.dispatchEvent(this, "ZipFile", name, entries, comment, entryCount);
    }

    @SimpleEvent(description = "Raises after ZipEntry has opened successfully.")
	public void ZipEntry(String name, boolean isDirectory, long size, long checksum, String content ) {
		EventDispatcher.dispatchEvent(this, "ZipEntry", name, isDirectory, size, checksum, content);
    }

    private String Read(InputStream inputStream) throws IOException, UnsupportedEncodingException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        inputStream.close();
        return result.toString("UTF-8");
    }

    /* 
        -----------------------
        Version

        Returns the version of the extension.

        -----------------------
    */
    @SimpleProperty(description = "Returns the extension version.")
    public int Version() {
        return Zip.class.getAnnotation(DesignerComponent.class).version();
    }
}
