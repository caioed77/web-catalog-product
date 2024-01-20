<template>
  <TransitionRoot as="template" :show="props.open">
    <Dialog as="div" class="relative z-10" @close="!props.open">
      <TransitionChild
        as="template"
        enter="ease-out duration-300"
        enter-from="opacity-0"
        enter-to="opacity-100"
        leave="ease-in duration-200"
        leave-from="opacity-100"
        leave-to="opacity-0"
      >
        <div
          class="fixed inset-0 hidden bg-gray-500 bg-opacity-75 transition-opacity md:block"
        ></div>
      </TransitionChild>

      <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div
          class="flex min-h-full items-stretch justify-center text-center md:items-center md:px-2 lg:px-4"
        >
          <TransitionChild
            as="template"
            enter="ease-out duration-300"
            enter-from="opacity-0 translate-y-4 md:translate-y-0 md:scale-95"
            enter-to="opacity-100 translate-y-0 md:scale-100"
            leave="ease-in duration-200"
            leave-from="opacity-100 translate-y-0 md:scale-100"
            leave-to="opacity-0 translate-y-4 md:translate-y-0 md:scale-95"
          >
            <DialogPanel
              class="flex w-full transform text-left text-base transition md:my-8 md:max-w-2xl md:px-4 lg:max-w-4xl"
            >
              <div
                class="relative flex w-full items-center overflow-hidden bg-white px-4 pb-8 pt-14 shadow-2xl sm:px-6 sm:pt-8 md:p-6 lg:p-8"
              >
                <button
                  type="button"
                  class="absolute right-4 top-4 text-gray-400 hover:text-gray-500 sm:right-6 sm:top-8 md:right-6 md:top-6 lg:right-8 lg:top-8"
                  @click="!props.open"
                >
                  <span class="sr-only">Close</span>
                  <PhX :size="24" class="h-6 w-6" aria-hidden="true" />
                </button>

                <div
                  class="grid grid-cols-1 md:grid-cols-2"
                  v-for="prod in product.cores"
                >
                  <img
                    :src="prod.imageSrc"
                    :alt="prod.imageAlt"
                    class="w-36 h-36 object-cover object-center"
                  />

                  <div class="sm:col-span-8 lg:col-span-7">
                    <h2 class="text-md font-bold text-gray-900 sm:pr-12">
                      {{ product.name }}
                    </h2>

                    <section aria-labelledby="information-heading" class="mt-2">
                      <p class="text-2xl text-gray-900">{{ product.price }}</p>

                      <!-- Reviews -->
                      <div class="mt-6">
                        <div class="flex items-center">
                          <div class="flex items-center">
                            <PhStar
                              :size="24"
                              v-for="rating in [0, 1, 2, 3, 4]"
                              :key="rating"
                              :class="[
                                product.rating > rating
                                  ? 'text-yellow-500'
                                  : 'text-gray-200',
                                'h-5 w-5 flex-shrink-0',
                              ]"
                              aria-hidden="true"
                            />
                          </div>
                          <p class="sr-only">
                            {{ product.rating }}
                          </p>
                        </div>
                      </div>
                    </section>
                  </div>
                </div>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>

<script setup lang="ts">
import {
  Dialog,
  DialogPanel,
  TransitionChild,
  TransitionRoot,
} from "@headlessui/vue";
import { PhStar, PhX } from "@phosphor-icons/vue";

const product = {
  id: 2,
  name: "Basic Tee 6-Pack ",
  price: "$192",
  rating: 3.9,
  cores: [
    {
      imageSrc:
        "https://tailwindui.com/img/ecommerce-images/product-quick-preview-02-detail.jpg",
      imageAlt: "Two each of gray, white, and black shirts arranged on table.",
    },
    {
      imageSrc:
        "https://tailwindui.com/img/ecommerce-images/product-quick-preview-02-detail.jpg",
      imageAlt: "Two each of gray, white, and black shirts arranged on table.",
    },
    {
      imageSrc:
        "https://tailwindui.com/img/ecommerce-images/product-quick-preview-02-detail.jpg",
      imageAlt: "Two each of gray, white, and black shirts arranged on table.",
    },
  ],
};

interface IProps {
  open: boolean;
}

const props = defineProps<IProps>();
</script>
