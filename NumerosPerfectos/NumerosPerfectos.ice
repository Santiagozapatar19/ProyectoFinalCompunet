module Demo {
    sequence<long> LongSeq;

    interface Worker {
        LongSeq findPerfectNumbers(long start, long end);
    };

    interface Master {
        void addWorker(string workerId, Worker* worker);
        LongSeq arrangeAnswer(long start, long end);  // Cambiado a LongSeq
    };
};