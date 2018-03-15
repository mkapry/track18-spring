
package ru.track.io;

        import org.jetbrains.annotations.NotNull;
        import org.jetbrains.annotations.Nullable;
        import ru.track.io.vendor.Bootstrapper;
        import ru.track.io.vendor.FileEncoder;

        import java.io.*;
        import java.net.InetSocketAddress;

public final class TaskImplementation implements FileEncoder {

    //public static final String finPath="/Users/Masha/track18-spring/L2-objects/image_256.png";
    //public static final String foutPath="/Users/Masha/track18-spring/L2-objects/based_file_.txt";

    @NotNull
    public static String encode (String s){
        String r="",p="";
        int c=s.length()%3;
        if (c>0){
            for (; c<3; c++){
                p+="=";
                s+="\0";
            }
        }
        for(c=0;c<s.length(); c+=3) {
            if (c > 0 && (c / 3 * 4) % 76 == 0)
                r += "\r\n";
            int n = (s.charAt(c) << 16) + (s.charAt(c + 1) << 8) + (s.charAt(c + 2));
            int n1 = (n >> 18) & 63, n2 = (n >> 12) & 63, n3 = (n >> 6) & 63, n4 = n & 63;
            r += "" + toBase64[n1] + toBase64[n2] + toBase64[n3] + toBase64[n4];
        }
        return r.substring(0,r.length()-p.length())+p;
    }


    /**
     * @param finPath  where to read binary data from
     * @param foutPath where to write encoded data. if null, please create and use temporary file.
     * @return file to read encoded data from
     * @throws IOException is case of input/output errors
     */
    public File encodeFile(@NotNull String finPath, @Nullable String foutPath) throws IOException {
        final File fin = new File(finPath);
        final File fout;
        if (foutPath != null) {
            fout = new File(foutPath);
        } else {
            fout = File.createTempFile("based_file_", ".txt");
            fout.deleteOnExit();
        }
        BufferedReader br=new BufferedReader(new FileReader(fin));
        String strline;
        FileWriter fw = new FileWriter(fout);
        while ((strline=br.readLine())!=null){
            String encodedline=encode(strline);
            fw.write(encodedline);
        };
        /* XXX: https://docs.oracle.com/javase/8/docs/api/java/io/File.html#deleteOnExit-- */
        //throw new UnsupportedOperationException(); // TODO: implement
        return fout;
    };

    private static final char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    public static void main(String[] args) throws Exception {
        final FileEncoder encoder = new TaskImplementation();
        // NOTE: open http://localhost:9000/ in your web browser
        (new Bootstrapper(args, encoder))
                .bootstrap("", new InetSocketAddress("127.0.0.1", 9000));
    }

}

