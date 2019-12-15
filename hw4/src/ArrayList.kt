class ArrayList<T>() : AbstractList<T>()
{
    //Просто так не дает создать Array<T>
    private var buff = Array<Any?>(8, {null}) as Array<T?>;
    private var bufferSize = 8;

    override var size: Int = 0;

    override fun get(index: Int): T {
        return buff[index] as T;
    }

    fun add(value : T) {
        if (size == bufferSize - 1) {
            buff = buff.copyOf(bufferSize * 2);
            bufferSize *= 2;
        }
        buff[size] = value;
        size ++;
    }

    fun delete(index: Int) : T {
        var value = get(index);
        var end = buff.copyOfRange(index + 1, bufferSize);
        var begin = buff.copyOfRange(0, index);
        //to keep dim
        var tmp:T? = null
        buff = begin + end + tmp;
        size --;
        return value;
    }

    fun insert(index: Int, value: T) {
        var end = buff.copyOfRange(index, bufferSize);
        var begin = buff.copyOfRange(0, index);
        buff = begin + value + end;
        size ++;
    }
}


fun main(args: Array<String>) {
    var arr = ArrayList<Int>();
    arr.add(0);
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    arr.add(6);
    arr.add(7);
    arr.add(8);
    arr.insert(5, 5);
    println(arr.get(0))
    println(arr.get(4));
    println(arr.delete(8));
    println(arr.delete(2));
    println(arr.delete(0));
    println(arr);
}