import java.util.Arrays;

/**
 * Created by m4dguy on 28.03.2015.
 */
public class MeshModifier {

    protected Mesh mesh;
    protected float displaceX = 0f;
    protected float displaceY = 0f;
    protected float scaling = 1f;
    protected float rotation = 0f;
    protected float[][] modified;

    public MeshModifier(){}

    public MeshModifier(Mesh m) {
        assignMesh(m);
        applyTransformations();
    }

    public void assignMesh(Mesh m){
        mesh = m;
        modified = new float[m.getNodeCount()][2];
        transformationReset();
    }

    public void setScaling(float s){
        scaling = s;
    }

    public void setRotation(float r){
        rotation = r;
    }

    public void setDisplacement(float dx, float dy){
        displaceX = dx;
        displaceY = dy;
    }

    /**
     * Resets the current Mesh back to its default by undoing all transformations.
     */
    public void transformationReset(){
        for (int i=0; i<mesh.nodes.length; ++i) {
            modified[i][0] = mesh.nodes[i][0];
            modified[i][1] = mesh.nodes[i][1];
            //modified[i] = Arrays.copyOf(mesh.nodes[i], mesh.nodes[i].length);
        }
    }

    /**
     * Reset and recalculate all transformation changes.
     */
    public void applyTransformations(){
        transformationReset();
        scale();
        rotate();
        displace();
    }

    /**
     * Scaling by given factor.
     * Be aware that this transformation is applied with the origin (0,0) as rotation point.
     * Apply this transformation before displacing the mesh to avoid problems.
     */
    public void scale(){
        for(int i=0; i<mesh.getNodeCount(); ++i){
            modified[i][0] *= scaling;
            modified[i][1] *= scaling;
        }
    }

    /**
     * Rotation of the Mesh by given angle.
     * Be aware that this transformation is applied with the origin (0,0) as rotation point.
     * Apply this transformation before displacing the mesh to avoid problems.
     */
    public void rotate(){
        if(rotation == 0)
            return;

        float sinA = (float) Math.sin(rotation);
        float cosA = (float) Math.cos(rotation);
        float n0, n1;

        for(int i=0; i<mesh.getNodeCount(); ++i){
            n0 = modified[i][0];
            n1 = modified[i][1];
            modified[i][0] = cosA * n0 - sinA * n1;
            modified[i][1] = sinA * n0 - cosA * n1;
        }
    }

    /**
     * Shifts the Mesh by given numbers.
     */
    public void displace(){
        for(int i=0; i<mesh.getNodeCount(); ++i){
            modified[i][0] += displaceX;
            modified[i][1] += displaceY;
        }
    }

    public float getBound(){
        return (mesh.bound*scaling);
    }

    public int getNodeCount(){
        return modified.length;
    }

    public int getEdgeCount(){
        return mesh.getEdgeCount();
    }

    public float[][] getNodes() {
        return modified;
    }

    public int[][] getEdges(){
        return mesh.edges;
    }
}
