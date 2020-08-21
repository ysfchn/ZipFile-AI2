# 🧱 ZipFile-AI2 `Extension`

Java's ZipFile implementation for App Inventor 2!

## 🧩 Blocks

<table style="width:100%">
    <tr>
        <th width="30%">Block</th>
        <th>Description</th>
    </tr>
    <!-- FILE  -->
    <tr>
        <td align="right">
            <img src="assets/blocks/method_file.png">
        </td>
        <td>
            Opens a new zip archive from storage.
        </td>
    </tr>
    <!-- ENTRY  -->
    <tr>
        <td align="right">
            <img src="assets/blocks/method_entry.png">
        </td>
        <td>
            Reads the content of one of file in the lastly opened archive.
        </td>
    </tr>
    <!-- EVENT ERROR  -->
    <tr>
        <td align="right">
            <img src="assets/blocks/event_error.png">
        </td>
        <td>
            Raises when one of methods resulted with error.
            <br><br>
            <code>method</code> - Name of the method that error raised in.
            <br>
            <code>message</code> - Error message.
        </td>
    </tr>
    <!-- EVENT ZIPFILE  -->
    <tr>
        <td align="right">
            <img src="assets/blocks/event_zipfile.png">
        </td>
        <td>
            Raises after ZipFile has opened successfully.
            <br><br>
            <code>name</code> - Name of the file.
            <br>
            <code>entries</code> - All entry names as list.
            <br>
            <code>comment</code> - Returns the comment of the archive.
            <br>
            <code>entryCount</code> - Count of the entries.
        </td>
    </tr>
    <!-- EVENT ZIPENTRY  -->
    <tr>
        <td align="right">
            <img src="assets/blocks/event_zipentry.png">
        </td>
        <td>
            Raises after ZipEntry has opened successfully.
            <br><br>
            <code>name</code> - Name of the entry.
            <br>
            <code>isDirectory</code> - Returns <code>true</code> if entry is a directory, otherwise <code>false</code>.
            <br>
            <code>size</code> - Returns the uncompressed size of the entry data.
            <br>
            <code>checksum</code> - Returns the CRC-32 checksum of the uncompressed entry data.
            <br>
            <code>content</code> - Content of the entry.
        </td>
    </tr>

</table>

## 🔨 Building

You will need:

-   Java 1.8 (either OpenJDK or Oracle)
-   Ant 1.10 or higher

Then execute `ant extensions` in the root of the repository.

## 🏅 License

Source code is licensed under MIT license. You must include the license notice in all copies or substantial uses of the work.
