const assert = require('assert');

describe('Test suite', function () {
  it('Test 1', function () {
    const someValue = 1;
    const expectedValue = 1;
    assert.equal(someValue, expectedValue, `Value "${someValue}" should be equal "${expectedValue}"`)
  })
})