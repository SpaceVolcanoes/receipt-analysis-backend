# Lombok

See [official documentation](https://projectlombok.org/features/all) for more elaborate info.

## Install IntelliJ Plugin named `Lombok`

After that, enable in settings:
Build, Execution, Deployment > Compiler > Annotation Processor > Enable annotation processing

... and most likely restart your IntelliJ to start recognizing Lombok magic.

## Do not use

- `@Data` and `@ToString`
- anything under `experimental` features

## Use

Only the annotations you need. PS. you can add `@Getter` on top of the class to cover all properties. 
