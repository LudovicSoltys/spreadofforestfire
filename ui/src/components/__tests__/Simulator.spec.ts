import { describe, it, expect } from 'vitest'

import { mount } from '@vue/test-utils'
import HelloWorld from '../Simulator.vue'

describe('Simulator', () => {
  it('renders properly', () => {
    const wrapper = mount(Simulator, { props: { msg: 'Hello Vitest' } })
    expect(wrapper.text()).toContain('Hello Vitest')
  })
})
