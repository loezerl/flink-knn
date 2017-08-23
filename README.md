# flink-knn

### Notas:

- Flink já está funcionando com a IDE.
- Implementar estrutura do Experimenter.
- Implementar os módulos do KNN.
- Implementar os módulos do Prequential.


Pelo jeito não adiantou usar a lib para carregar os arquivos arff, tenho que dar um jeito de conseguir um `DataSet<Instance>` para realizar as outras operações.


**Como carregar os arquivos arff grandes:**

```

ArffFileStream filess = new ArffFileStream("/home/loezerl-fworks/Downloads/kyoto.arff", 8);
//
        InstanceExample olar = filess.nextInstance();

        while(filess.hasMoreInstances()){
            olar = filess.nextInstance();
            System.out.println(olar.getData());
        }
        
```


Descobrir o class index da base de kyoto