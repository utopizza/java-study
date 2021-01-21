package designPatterns;

import java.io.*;

public class PrototypePattern {
    public static void main(String[] args) {
        new ShallowClone().ShallowCloneTest();
        new DeepClone().DeepCloneTest();
        new DeepCloneBySerialization().DeepCloneBySerializationTest();
    }
}

class ShallowClone {

    private class Head implements Cloneable {
        private Face face;

        public Head(Face face) {
            this.face = face;
        }

        @Override
        public Head clone() {
            Head headClone = null;
            try {
                headClone = (Head) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return headClone;
        }
    }

    private class Face implements Cloneable {
        @Override
        public Face clone() {
            Face faceClone = null;
            try {
                faceClone = (Face) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return faceClone;
        }
    }

    public void ShallowCloneTest() {
        System.out.println("Shallow Clone:");
        Head head1 = new Head(new Face());
        Head head2 = head1.clone();
        System.out.println("head1:" + head1 + "   face1:" + head1.face);
        System.out.println("head2:" + head2 + "   face2:" + head2.face);
    }

}

class DeepClone {
    private class Head implements Cloneable {
        private Face face;

        public Head(Face face) {
            this.face = face;
        }

        @Override
        public Head clone() {
            Head headClone = null;
            try {
                headClone = (Head) super.clone();
                headClone.face = face.clone(); // clone the members manually
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return headClone;
        }
    }

    private class Face implements Cloneable {
        @Override
        public Face clone() {
            Face faceClone = null;
            try {
                faceClone = (Face) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return faceClone;
        }
    }

    public void DeepCloneTest() {
        System.out.println("Deep clone:");
        Head head1 = new Head(new Face());
        Head head2 = head1.clone();
        System.out.println("head1:" + head1 + "   face1:" + head1.face);
        System.out.println("head2:" + head2 + "   face2:" + head2.face);
    }
}

class DeepCloneBySerialization {
    // why it has to be static class to serialization?
    private static class Head implements Serializable {
        private Face face;

        public Head(Face face) {
            this.face = face;
        }

        public Head DeepClone() {
            Head headClone = null;
            try {
                // write into stream
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(this);
                // read from stream
                ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bis);
                // return cloned object
                headClone = (Head) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return headClone;
        }
    }

    private static class Face implements Serializable {
    }

    public void DeepCloneBySerializationTest() {
        System.out.println("Deep Clone By Serialization:");
        Head head1 = new Head(new Face());
        Head head2 = head1.DeepClone();
        System.out.println("head1:" + head1 + "   face1:" + head1.face);
        System.out.println("head2:" + head2 + "   face2:" + head2.face);
    }
}