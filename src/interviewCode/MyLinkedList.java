package interviewCode;

public class MyLinkedList {
    public static void main(String[] args) {
        Node head = new Node(4);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(6);
        head.next = b;
        b.next = c;
        c.next = d;

        Node h = new LinkedListSelectSort().sort(head);

        while (h != null) {
            System.out.print(h.value + " ");
            h = h.next;
        }
    }
}

class Node {
    int value;
    Node next;

    Node(int value) {
        this.value = value;
    }
}

class RemoveLastKthNode {
    public Node removeLastKthNode(Node head, int k) {
        if (head == null || k < 1) return head;
        Node tempHead = new Node(0);
        tempHead.next = head;
        Node fast = tempHead, slow = fast;
        while (fast.next != null && k > 0) {
            fast = fast.next;
            k--;
        }
        if (k >= 0) return head.next;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        if (slow.next != null) slow.next = slow.next.next;
        return tempHead.next;
    }
}

class RemoveMiddleNode {
    public Node removeMiddleNode(Node head) {
        if (head == null || head.next == null) return head;
        if (head.next.next == null) return head.next;
        Node pre = head, cur = head.next.next;
        while (cur.next != null && cur.next.next != null) {
            pre = pre.next;
            cur = cur.next.next;
        }
        pre.next = pre.next.next;
        return head;
    }
}

class ReverseLinkedList {
    public Node reverse(Node head) {
        if (head == null || head.next == null) return head;
        Node p = head, q = head.next, r = null;
        head.next = null;
        while (q != null) {
            r = q.next;
            q.next = p;
            p = q;
            q = r;
        }
        return p;
    }

    public Node reverse2(Node head) {
        Node pre = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public Node reverse3(Node head) {
        if (head == null || head.next == null) return head;
        Node newHead = reverse3(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}

class ReverseLinkedListPartially {
    public Node reversePart(Node head, int from, int to) {
        // count len
        int len = 0;
        Node p = head;
        while (p != null) {
            len++;
            p = p.next;
        }
        if (!(1 <= from && from <= to && to <= len)) return head;

        // find starting node
        p = head;
        for (int i = 1; i < from - 1; i++) p = p.next;
        Node start = p, fromNode = from == 1 ? p : p.next;

        // find ending node
        p = head;
        for (int i = 1; i < to; i++) p = p.next;
        Node toNode = p, end = p.next;

        // reverse nodes between starting node and ending node
        p = fromNode;
        Node q = p.next, r = null;
        p.next = null;
        while (q != end) {
            r = q.next;
            q.next = p;
            p = q;
            q = r;
        }

        // connect reversed list back
        if (from == 1) {
            fromNode.next = end;
            return toNode;
        } else {
            fromNode.next = end;
            start.next = toNode;
            return head;
        }
    }
}

class ReverseLinkedListGroupByK {
    public Node reverse(Node head, int k) {
        if (head == null || head.next == null) return head;

        // count len
        int len = 0;
        Node p = head;
        while (p != null) {
            len++;
            p = p.next;
        }
        if (k <= 1 || k > len) return head;

        // put linked list into arr
        Node[] arr = new Node[len];
        p = head;
        int index = 0;
        while (p != null) {
            arr[index++] = p;
            p = p.next;
        }

        // reverse by k
        for (int i = 0; i <= len - k; i += k) {
            int j = i + k - 1;
            reverseArr(arr, i, j);
        }

        // recover linked list
        for (int i = 0; i < len - 1; i++) {
            arr[i].next = arr[i + 1];
        }
        arr[len - 1].next = null;

        return arr[0];
    }

    private void reverseArr(Node[] arr, int i, int j) {
        while (i < j) {
            swap(arr, i++, j--);
        }
    }

    private void swap(Node[] arr, int i, int j) {
        Node temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

class JosephusKill {
    public Node JosephusKill(Node head, int m) {
        if (head == null || head.next == head || m < 1) return head;

        // find last node in the recycle linked list
        Node last = head;
        while (last.next != head) last = last.next;

        // count and kill
        int count = 0;
        while (head != last) {
            count++;
            if (count == m) {
                last.next = head.next;
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }
}

class IsPalindrome {
    public boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        // find middle node
        Node mid = head, right = head;
        while (right != null && right.next.next != null) {
            mid = mid.next;
            right = right.next.next;
        }

        // reverse right part
        Node p = mid, q = mid.next, r = null;
        p.next = null;
        while (q != null) {
            r = q.next;
            q.next = p;
            p = q;
            q = r;
        }

        // compare
        Node fromLeft = head, fromRight = p; // now p is right head
        boolean isPalindrome = true;
        while (fromLeft != null && fromRight != null) {
            if (fromLeft.value != fromRight.value) {
                isPalindrome = false;
                break;
            }
            fromLeft = fromLeft.next;
            fromRight = fromRight.next;
        }

        // recover
        q = p.next;
        p.next = null;
        while (q != null) {
            r = q.next;
            q.next = p;
            p = q;
            q = r;
        }

        return isPalindrome;
    }
}

class LinkedListPartition {
    public Node partition(Node head, int pivot) {
        if (head == null || head.next == null) return head;

        // count len
        int len = 0;
        Node p = head;
        while (p != null) {
            len++;
            p = p.next;
        }

        // list to arr
        Node[] arr = new Node[len];
        p = head;
        int index = 0;
        while (p != null) {
            arr[index++] = p;
            p = p.next;
        }

        // partition arr
        int small = -1, big = len, i = 0; // small and big is the sorted index
        while (i != big) {
            if (arr[i].value < pivot) swap(arr, ++small, i++);
            else if (arr[i].value == pivot) i++;
            else swap(arr, --big, i);
        }

        // recover list
        for (i = 0; i < len - 1; i++) {
            arr[i].next = arr[i + 1];
        }
        arr[len - 1].next = null;

        return arr[0];
    }

    private void swap(Node[] arr, int i, int j) {
        Node temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

class LinkedListCycle {
    public Node findCycle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                Node slow2 = head;
                while (slow != slow2) {
                    slow = slow.next;
                    slow2 = slow2.next;
                }
                return slow;
            }
        }
        return null;
    }
}

class LinkedListIntersection {
    public Node findIntersection(Node head1, Node head2) {
        if (head1 == null || head2 == null) return null;
        Node a = head1, b = head2;
        while (a != b) {
            a = a == null ? head2 : a.next;
            b = b == null ? head1 : b.next;
        }
        return a;
    }
}

class LinkedListSelectSort {
    public Node sort(Node head) {
        if (head == null || head.next == null) return head;
        Node tempHead = new Node(0);
        Node p = tempHead;
        while (head != null) {
            Node q = head, minNodePre = null;
            int min = q.value;
            // find minNode's preNode
            while (q != null && q.next != null) {
                if (q.next.value < min) {
                    minNodePre = q;
                    min = q.next.value;
                }
                q = q.next;
            }
            // delete minNode from old list and add to new list
            if (minNodePre == null) { // minNode is head
                p.next = head;
                p = p.next;
                head = head.next;
                p.next = null;
            } else { // minNode is not head
                p.next = minNodePre.next;
                p = p.next;
                minNodePre.next = minNodePre.next.next;
                p.next = null;
            }
        }
        return tempHead.next;
    }
}

class RecycleLinkedList {
    public Node add(Node head, int num) {

        if (head == null) {
            Node node = new Node(num);
            node.next = node;
            return node;
        }

        // find tail
        Node tail = head;
        while (tail.next != head) tail = tail.next;

        // insert before head: num<head, and return new head(keep head node being min node)
        if (num < head.value) {
            Node node = new Node(num);
            node.next = head;
            tail.next = node;
            return node;
        }

        // insert after tail: num>tail, and return old head
        if (num > tail.value) {
            Node node = new Node(num);
            node.next = head;
            tail.next = node;
            return head;
        }

        // insert between head and tail: head.value<=num<=tail.num
        Node cur = head;
        while (cur != null && cur.next != null) {
            if (cur.value <= num && num <= cur.next.value) {
                Node node = new Node(num);
                node.next = cur.next;
                cur.next = node;
                return head;
            }
        }

        return head;
    }
}