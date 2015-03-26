import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by m4dguy on 15.03.2015.
 */
public final class MeshLoader {

    protected static Mesh loadVectorObject(String path){
        Mesh m;

        int nodeCount;
        int edgeCount;
        float[][] nodes;
        int[][] edges;

        ArrayList<String> lines = new ArrayList<String>();
        int n = 0;      //number of nodes
        int e = 0;      //number of edges

        //count number of nodes and edges
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            lines.add(line);
            while(line != null){
                if(line.charAt(0) == 'v'){
                    ++n;
                }
                if(line.charAt(0) == 'e'){
                    ++e;
                }
                line = br.readLine();
                lines.add(line);
            }
        }catch(Exception except){System.out.println(e);}

        //construct data
        nodeCount = n;
        edgeCount = e;
        nodes = new float[nodeCount][2];
        //nodesDisplaced = new float[m.nodeCount][2];
        edges = new int[edgeCount][2];

        n = 0;
        e = 0;
        ArrayList<String> tokens;
        for(String s: lines) {
            if (s == null)
                continue;

            tokens = UtilsProcessing.splitByWhitespace(s);
            if(tokens.get(0).equals("v")) {
                nodes[n][0] = Float.parseFloat(tokens.get(1));
                nodes[n][1] = Float.parseFloat(tokens.get(2));
                //nodesDisplaced[n][0] = Float.parseFloat(tokens.get(1));
                //nodesDisplaced[n][1] = Float.parseFloat(tokens.get(2));
                ++n;
            }
            if(tokens.get(0).equals("e")) {
                edges[e][0] = Integer.parseInt(tokens.get(1));
                edges[e][1] = Integer.parseInt(tokens.get(2));
                ++e;
            }
        }


        m = new Mesh(nodes, edges);
        m.align();
        m.calcBound();
        return m;
    }

}
