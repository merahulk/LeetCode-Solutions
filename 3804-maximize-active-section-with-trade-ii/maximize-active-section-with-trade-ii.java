import java.util.*;

class Solution {
    static class Group {
        int start;
        int length;

        Group(int start, int length) {
            this.start = start;
            this.length = length;
        }
    }

    static class SparseTable {
        private final int n;
        private final int[][] st;

        public SparseTable(int[] nums) {
            this.n = nums.length;
            if (n == 0) {
                this.st = new int[0][0];
                return;
            }
            int k = Integer.SIZE - Integer.numberOfLeadingZeros(n);
            this.st = new int[k][n];

            for (int j = 0; j < n; j++) {
                st[0][j] = nums[j];
            }

            for (int i = 1; i < k; i++) {
                for (int j = 0; j + (1 << i) <= n; j++) {
                    st[i][j] = Math.max(st[i - 1][j], st[i - 1][j + (1 << (i - 1))]);
                }
            }
        }

        public int query(int l, int r) {
            if (l > r || n == 0) return 0;
            int k = Integer.SIZE - Integer.numberOfLeadingZeros(r - l + 1) - 1;
            return Math.max(st[k][l], st[k][r - (1 << k) + 1]);
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int ones = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') ones++;
        }

        List<Group> zeroGroups = new ArrayList<>();
        int[] zeroGroupIndex = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                if (i > 0 && s.charAt(i - 1) == '0') {
                    zeroGroups.get(zeroGroups.size() - 1).length++;
                } else {
                    zeroGroups.add(new Group(i, 1));
                }
            }
            zeroGroupIndex[i] = zeroGroups.size() - 1;
        }

        if (zeroGroups.isEmpty()) {
            List<Integer> ans = new ArrayList<>(queries.length);
            for (int k = 0; k < queries.length; k++) ans.add(ones);
            return ans;
        }

        int m = zeroGroups.size();
        int[] zeroMergeLengths = new int[Math.max(0, m - 1)];
        for (int i = 0; i < m - 1; i++) {
            zeroMergeLengths[i] = zeroGroups.get(i).length + zeroGroups.get(i + 1).length;
        }

        SparseTable st = new SparseTable(zeroMergeLengths);
        List<Integer> ans = new ArrayList<>(queries.length);

        for (int[] query : queries) {
            int l = query[0];
            int r = query[1];

            int gL = zeroGroupIndex[l];
            int gR = zeroGroupIndex[r];

            int left = (gL == -1) ? -1 : (zeroGroups.get(gL).length - (l - zeroGroups.get(gL).start));
            int right = (gR == -1) ? -1 : (r - zeroGroups.get(gR).start + 1);

            int startAdjacentGroupIndex = gL + 1;
            int endAdjacentGroupIndex = (s.charAt(r) == '1' ? gR : gR - 1) - 1;

            int activeSections = ones;

            if (s.charAt(l) == '0' && s.charAt(r) == '0' && gL + 1 == gR) {
                activeSections = Math.max(activeSections, ones + left + right);
            } else if (startAdjacentGroupIndex <= endAdjacentGroupIndex) {
                activeSections = Math.max(activeSections, ones + st.query(startAdjacentGroupIndex, endAdjacentGroupIndex));
            }

            if (s.charAt(l) == '0' && gL + 1 <= (s.charAt(r) == '1' ? gR : gR - 1)) {
                activeSections = Math.max(activeSections, ones + left + zeroGroups.get(gL + 1).length);
            }

            if (s.charAt(r) == '0' && gL < gR - 1) {
                activeSections = Math.max(activeSections, ones + right + zeroGroups.get(gR - 1).length);
            }

            ans.add(activeSections);
        }

        return ans;
    }
}