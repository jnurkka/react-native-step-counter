# react-native-step-counter

Simple step counter for react native apps

## Installation

```sh
npm install react-native-step-counter
```

## Usage

```js
import StepCounter, { useStepListener } from 'react-native-step-counter';

// call start to start listening to values from accelerometer
StepCounter.start();

// create a listener that receives a callback when accelerometer returns new values, the return value of this hook is a cleanup function
// note that you have to have called start() before starting the listener, otherwise it wont work
const removeListener = useStepListener(callback);

// somwhere later you can stop listening to values
removeListener();
StepCounter.stop();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
