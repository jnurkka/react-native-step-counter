# react-native-step-counter

Simple step counter for react native apps

## Installation

```sh
npm install react-native-step-counter
```

## Usage

```js
import StepCounter, { useAccelerometer } from 'react-native-step-counter';

// ...

// create a listener that receives a callback when accelerometer returns new values, the return value of this hook is a cleanup function
const removeListener = useAccelerometer(callback);

// call start to start listening to values from accelerometer
StepCounter.start();

// somwhere later you can stop listening to values
removeListener();
StepCounter.stop();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
