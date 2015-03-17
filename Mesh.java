import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by m4dguy on 24.02.2015.
 */
public class Mesh {

    protected int nodeCount;
    protected int edgeCount;

    float bound;
    float centerX, centerY;
    protected float[][] nodes;
    protected float[][] nodesDisplaced;
    protected int[][] edges;


    public Mesh(float[][] otherNodes, int[][] otherEdges) {
        nodes = otherNodes;
        edges = otherEdges;
        nodeCount = otherNodes.length;
        edgeCount = otherEdges.length;
        align();
        calcBound();
    }

    public Mesh(String path) {
        loadVectorObject(path);
        align();
        calcBound();
    }

    protected void loadVectorObject(String path){
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
        nodesDisplaced = new float[nodeCount][2];
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
                nodesDisplaced[n][0] = Float.parseFloat(tokens.get(1));
                nodesDisplaced[n][1] = Float.parseFloat(tokens.get(2));
                ++n;
            }
            if(tokens.get(0).equals("e")) {
                edges[e][0] = Integer.parseInt(tokens.get(1));
                edges[e][1] = Integer.parseInt(tokens.get(2));
                ++e;
            }
        }
    }

    public int getNodes(){
        return nodeCount;
    }

    public int getEdges(){
        return edgeCount;
    }

    public void setCenter(float x, float y){
        centerX = x;
        centerY = y;
    }

    public void transformationReset() {
        for (int i=0; i<nodes.length; ++i) {
            //System.arraycopy(nodes[i], 0, nodesDisplaced[i], 0, nodes[i].length);
            nodesDisplaced[i] = Arrays.copyOf(nodes[i], nodes[i].length);
        }
    }

    public boolean collision(Mesh m){
        float distance;

        float pos = 0f;     //value of interpolator
        float x1, y1;       //position of point
        float x2, y2;       //closest position of point on line

        float px1, py1;     //position of first node
        float px2, py2;     //position of second node


        //calculate line position

        //first check:
        //all nodes of this mesh with all edges of the other mesh
        for(int i=0; i<this.nodes.length; ++i) {
            x1 = this.nodesDisplaced[i][0];
            y1 = this.nodesDisplaced[i][1];

            for(int j=0; j<m.edges.length; ++j) {
                px1 = m.nodesDisplaced[m.edges[j][0]][0];
                py1 = m.nodesDisplaced[m.edges[j][0]][1];
                px2 = m.nodesDisplaced[m.edges[j][1]][0];
                py2 = m.nodesDisplaced[m.edges[j][1]][1];

                pos = (px2 - x1)/(px1 - px2) + (py2 - y1)/(py1 - py2);

                if ((pos > 1f) || (pos < 0f))
                    return false;

                x2 = px1 * pos + px2 * (1f - pos);
                y2 = py1 * pos + py2 * (1f - pos);

                //calculate actual distance
                distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
                distance = (float) Math.sqrt(distance);
                if (distance <= Engine.collisionDistance)
                    return true;
            }
        }

        //second check:
        //all nodes of the mesh with all edges of this mesh
        for(int i=0; i<m.nodes.length; ++i) {
            x1 = m.nodesDisplaced[i][0];
            y1 = m.nodesDisplaced[i][1];

            for(int j=0; j<this.edges.length; ++j) {
                px1 = this.nodesDisplaced[this.edges[j][0]][0];
                py1 = this.nodesDisplaced[this.edges[j][0]][1];
                px2 = this.nodesDisplaced[this.edges[j][1]][0];
                py2 = this.nodesDisplaced[this.edges[j][1]][1];

                pos = (px2 - x1)/(px1 - px2) + (py2 - y1)/(py1 - py2);

                if ((pos > 1f) || (pos < 0f))
                    return false;

                x2 = px1 * pos + px2 * (1f - pos);
                y2 = py1 * pos + py2 * (1f - pos);

                //calculate actual distance
                distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
                distance = (float) Math.sqrt(distance);
                if (distance <= Engine.collisionDistance)
                    return true;
            }
        }

        return false;
    }

    protected void displace(float x, float y){
        for(int i=0; i<nodeCount; ++i){
            nodesDisplaced[i][0] += x;
            nodesDisplaced[i][1] += y;
        }
    }

    protected void rotate(float angle){
        float sinA = (float) Math.sin(angle);
        float cosA = (float) Math.cos(angle);
        float n0, n1;

        for(int i=0; i<nodeCount; ++i){
            n0 = nodesDisplaced[i][0];
            n1 = nodesDisplaced[i][1];
            nodesDisplaced[i][0] = cosA * n0 - sinA * n1;
            nodesDisplaced[i][1] = sinA * n0 - cosA * n1;
        }
    }

    protected void scale(float s){
        for(int i=0; i<nodeCount; ++i){
            nodesDisplaced[i][0] *= s;
            nodesDisplaced[i][1] *= s;
        }
    }

    public void render(Image img){
        Graphics g = img.getGraphics();
        g.setColor(new Color(1f, 1f, 1f));

        int i, j;
        for(int e=0; e<edgeCount; ++e) {
            i = edges[e][0];
            j = edges[e][1];
            g.drawLine((int) nodesDisplaced[i][0], (int) nodesDisplaced[i][1], (int)nodesDisplaced[j][0], (int)nodesDisplaced[j][1]);
        }
    }

    public void calcBound(){
        bound = 0f;
        float dx, dy;
        for(int i=0; i<nodeCount; ++i) {
            dx = centerX - nodes[i][0];
            dy = centerY - nodes[i][1];
            bound = (float)Math.max(Math.sqrt(dx*dx + dy*dy), bound);
        }
    }

    protected void calcCenterX(){
        for(int i=0; i<nodeCount; ++i){
            centerX += nodes[i][0];
        }
        centerX /= nodeCount;
    }

    protected void calcCenterY(){
        for(int i=0; i<nodeCount; ++i){
            centerY += nodes[i][1];
        }
        centerY /= nodeCount;
    }

    //move mesh to origin
    protected void align(){
        calcCenterX();
        calcCenterY();
        for(int i=0; i<nodeCount; ++i){
            nodes[i][0] -= centerX;
            nodes[i][1] -= centerY;
        }
    }

}
