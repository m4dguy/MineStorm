import java.util.Arrays;

/**
 * Object for storing vector graphic information.
 * Contains methods for construction, manipulation and collision checks.
 * Carries node and edge information about the object.
 * The nodes are stored in an unmodified (nodes) and a transformed representation (nodesDisplaced).
 *
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


    /**
     * Constructor without any initialization.
     * It's DIY Mesh construction.
     */
    public Mesh(){ }

    /**
     * Mesh construction with custom arrays.
     * Make sure the arrays are actually valid!
     * @param otherNodes absolute node positions.
     * @param otherEdges edge list starting at 0.
     */
    public Mesh(float[][] otherNodes, int[][] otherEdges) {
        nodes = otherNodes;
        edges = otherEdges;
        nodeCount = otherNodes.length;
        edgeCount = otherEdges.length;

        nodesDisplaced = new float[nodeCount][2];
        for (int i=0; i<nodes.length; ++i) {
            nodesDisplaced[i] = Arrays.copyOf(nodes[i], nodes[i].length);
        }

        align();
        calcBound();
    }

    /**
     * Returns the size of the node array.
     * @return number of nodes.
     */
    public int getNodes(){
        return nodeCount;
    }

    /**
     * Returns the size of the edge array.
     * @return number of edges.
     */
    public int getEdges(){
        return edgeCount;
    }

    /**
     * Manually set the center of the Mesh.
     * This center is treated as a reference point for all transformations (displacement, scaling and rotation).
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public void setCenter(float x, float y){
        centerX = x;
        centerY = y;
    }

    /**
     * Resets the current Mesh back to its default by undoing all transformations.
     */
    public void transformationReset() {
        for (int i=0; i<nodes.length; ++i) {
            //System.arraycopy(nodes[i], 0, nodesDisplaced[i], 0, nodes[i].length);
            nodesDisplaced[i] = Arrays.copyOf(nodes[i], nodes[i].length);
        }
    }

    /**
     * Checks if two Meshes collide.
     * Checks if one of the nodes intersect with one of the edge of the other Mesh.
     * Calculates an exact distance and checks if it is smaller than a predefined collision distance.
     * @param m the other Mesh.
     * @return true, if both meshes collide.
     */
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

    /**
     * Shifts the Mesh by given numbers.
     * @param x shift in x direction.
     * @param y shift in y direction.
     */
    protected void displace(float x, float y){
        for(int i=0; i<nodeCount; ++i){
            nodesDisplaced[i][0] += x;
            nodesDisplaced[i][1] += y;
        }
    }

    /**
     * Rotation of the Mesh by given angle.
     * Be aware that this transformation is applied with the origin (0,0) as rotation point.
     * Apply this transformation before displacing the mesh to avoid problems.
     * @param angle rotation angle.
     */
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

    /**
     * Scaling by given factor.
     * Be aware that this transformation is applied with the origin (0,0) as rotation point.
     * Apply this transformation before displacing the mesh to avoid problems.
     * @param s scaling factor.
     */
    protected void scale(float s){
        for(int i=0; i<nodeCount; ++i){
            nodesDisplaced[i][0] *= s;
            nodesDisplaced[i][1] *= s;
        }
    }

    /**
     * Calculates and stores the maximum radius of this Mesh for the purpose of constructing a bounding sphere.
     */
    public void calcBound(){
        bound = 0f;
        float dx, dy;
        for(int i=0; i<nodeCount; ++i) {
            dx = centerX - nodes[i][0];
            dy = centerY - nodes[i][1];
            bound = (float)Math.max(Math.sqrt(dx*dx + dy*dy), bound);
        }
    }

    /**
     * Calculates and stores the center coordinate of this Mesh (x position).
     */
    protected void calcCenterX(){
        for(int i=0; i<nodeCount; ++i){
            centerX += nodes[i][0];
        }
        centerX /= nodeCount;
    }

    /**
     * Calculates and stores the center coordinate of this Mesh (y position).
     */
    protected void calcCenterY(){
        for(int i=0; i<nodeCount; ++i){
            centerY += nodes[i][1];
        }
        centerY /= nodeCount;
    }

    /**
     * Aligns the Mesh by moving it to the origin.
     * Useful for preparing subsequent transformations.
     */
    protected void align(){
        calcCenterX();
        calcCenterY();
        for(int i=0; i<nodeCount; ++i){
            nodes[i][0] -= centerX;
            nodes[i][1] -= centerY;
        }
    }

}
