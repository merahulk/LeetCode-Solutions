class Solution {
    class Node {
        char lc, rc;
        int len, pref, suff, best;
        
        Node(char lc, char rc, int len, int pref, int suff, int best) {
            this.lc = lc;
            this.rc = rc;
            this.len = len;
            this.pref = pref;
            this.suff = suff;
            this.best = best;
        }
    }

    private Node[] tree;
    private char[] chars;

    private Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        int len = left.len + right.len;
        
        // Left character of the combined node is always left child's left character
        char lc = left.lc;
        // Right character of the combined node is always right child's right character
        char rc = right.rc;

        // Prefix length
        int pref = left.pref;
        if (left.pref == left.len && left.rc == right.lc) {
            pref = left.len + right.pref;
        }

        // Suffix length
        int suff = right.suff;
        if (right.suff == right.len && right.lc == left.rc) {
            suff = right.len + left.suff;
        }

        // Best repeating substring length
        int best = Math.max(left.best, right.best);
        if (left.rc == right.lc) {
            best = Math.max(best, left.suff + right.pref);
        }

        return new Node(lc, rc, len, pref, suff, best);
    }

    private void build(int node, int start, int end) {
        if (start == end) {
            char c = chars[start];
            tree[node] = new Node(c, c, 1, 1, 1, 1);
            return;
        }
        int mid = start + (end - start) / 2;
        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);
        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(int node, int start, int end, int idx, char c) {
        if (start == end) {
            tree[node] = new Node(c, c, 1, 1, 1, 1);
            return;
        }
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            update(node * 2, start, mid, idx, c);
        } else {
            update(node * 2 + 1, mid + 1, end, idx, c);
        }
        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        this.chars = s.toCharArray();
        int n = s.length();
        tree = new Node[4 * n];
        build(1, 0, n - 1);

        int k = queryCharacters.length();
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char c = queryCharacters.charAt(i);
            
            chars[idx] = c;
            update(1, 0, n - 1, idx, c);
            ans[i] = tree[1].best;
        }

        return ans;
    }
}