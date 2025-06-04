module App {
    sequence<string> arrayS;
    interface Worker {
        arrayS findPerfectNumbers(long start, long end);
    }

    interface ClientCallback {
         void reportResults(arrayS numbers);
    }

    interface Master {
        void findPerfectNumbers(long start, long end, ClientCallback* cb);
    }

}