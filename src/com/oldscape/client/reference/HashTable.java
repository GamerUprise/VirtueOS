package com.oldscape.client.reference;

final class HashTable {
    private final int size;
    private final Node[] buckets;
    private Node currentGet;
    private Node current;
    private int index;

    public HashTable(final int var1) {
        this.index = 0;
        this.size = var1;
        this.buckets = new Node[var1];

        for (int var2 = 0; var2 < var1; ++var2) {
            final Node var3 = this.buckets[var2] = new Node();
            var3.next = var3;
            var3.previous = var3;
        }

    }

    public Node get(final long var1) {
        final Node var3 = this.buckets[(int) (var1 & (this.size - 1))];

        for (this.currentGet = var3.next; var3 != this.currentGet; this.currentGet = this.currentGet.next) {
            if (this.currentGet.hash == var1) {
                final Node var4 = this.currentGet;
                this.currentGet = this.currentGet.next;
                return var4;
            }
        }

        this.currentGet = null;
        return null;
    }

    public void put(final Node var1, final long var2) {
        if (var1.previous != null) {
            var1.unlink();
        }

        final Node var4 = this.buckets[(int) (var2 & (this.size - 1))];
        var1.previous = var4.previous;
        var1.next = var4;
        var1.previous.next = var1;
        var1.next.previous = var1;
        var1.hash = var2;
    }

    void clear() {
        for (int var1 = 0; var1 < this.size; ++var1) {
            final Node var2 = this.buckets[var1];

            while (true) {
                final Node var3 = var2.next;
                if (var3 == var2) {
                    break;
                }

                var3.unlink();
            }
        }

        this.currentGet = null;
        this.current = null;
    }

    public Node first() {
        this.index = 0;
        return this.next();
    }

    public Node next() {
        Node var1;
        if (this.index > 0 && this.buckets[this.index - 1] != this.current) {
            var1 = this.current;
        } else {
            do {
                if (this.index >= this.size) {
                    return null;
                }

                var1 = this.buckets[this.index++].next;
            } while (var1 == this.buckets[this.index - 1]);

        }
        this.current = var1.next;
        return var1;
    }
}
